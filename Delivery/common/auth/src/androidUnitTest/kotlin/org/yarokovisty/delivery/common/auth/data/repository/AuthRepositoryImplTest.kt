package org.yarokovisty.delivery.common.auth.data.repository

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.yarokovisty.delivery.common.auth.data.datasource.AuthLocalDataSource
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class AuthRepositoryImplTest {

    private val localDataSource: AuthLocalDataSource = mockk()
    private val repository = AuthRepositoryImpl(localDataSource)

    @Test
    fun `save token EXPECT invoke save token by local data source`() = runTest {
        val token = "test_token_abc123"
        coEvery { localDataSource.saveToken(token) } returns Unit

        repository.saveToken(token)

        coVerify { localDataSource.saveToken(token) }
    }

    @Test
    fun `get token EXPECT token`() = runTest {
        val expected = "test_token_abc123"
        coEvery { localDataSource.getToken() } returns expected

        val actual = repository.getToken()

        assertEquals(expected, actual)
    }

    @Test
    fun `get token EXPECT invoke get token by local data source`() = runTest {
        coEvery { localDataSource.getToken() } returns "test_token_abc123"

        repository.getToken()

        coVerify { localDataSource.getToken() }
    }

    @Test
    fun `get token when not saved EXPECT null`() = runTest {
        coEvery { localDataSource.getToken() } returns null

        val actual = repository.getToken()

        assertNull(actual)
    }

    @Test
    fun `clear token EXPECT invoke clear token by local data source`() = runTest {
        coEvery { localDataSource.clearToken() } returns Unit

        repository.clearToken()

        coVerify { localDataSource.clearToken() }
    }
}
