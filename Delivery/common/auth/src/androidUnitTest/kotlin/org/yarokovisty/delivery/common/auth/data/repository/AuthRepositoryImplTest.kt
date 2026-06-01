package org.yarokovisty.delivery.common.auth.data.repository

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.yarokovisty.delivery.core.network.token.TokenProvider
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class AuthRepositoryImplTest {

    private val tokenProvider: TokenProvider = mockk()
    private val repository = AuthRepositoryImpl(tokenProvider)

    @Test
    fun `save token EXPECT invoke set by token provider`() = runTest {
        val token = "test_token_abc123"
        coEvery { tokenProvider.set(token) } returns Unit

        repository.saveToken(token)

        coVerify { tokenProvider.set(token) }
    }

    @Test
    fun `get token EXPECT token`() = runTest {
        val expected = "test_token_abc123"
        coEvery { tokenProvider.get() } returns expected

        val actual = repository.getToken()

        assertEquals(expected, actual)
    }

    @Test
    fun `get token EXPECT invoke get by token provider`() = runTest {
        coEvery { tokenProvider.get() } returns "test_token_abc123"

        repository.getToken()

        coVerify { tokenProvider.get() }
    }

    @Test
    fun `get token when not saved EXPECT null`() = runTest {
        coEvery { tokenProvider.get() } returns null

        val actual = repository.getToken()

        assertNull(actual)
    }

    @Test
    fun `clear token EXPECT invoke clear by token provider`() = runTest {
        coEvery { tokenProvider.clear() } returns Unit

        repository.clearToken()

        coVerify { tokenProvider.clear() }
    }
}
