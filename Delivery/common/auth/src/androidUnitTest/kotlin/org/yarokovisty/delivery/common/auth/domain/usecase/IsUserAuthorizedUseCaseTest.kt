package org.yarokovisty.delivery.common.auth.domain.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.yarokovisty.delivery.common.auth.domain.repository.AuthRepository
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class IsUserAuthorizedUseCaseTest {

    private val repository: AuthRepository = mockk()
    private val useCase = IsUserAuthorizedUseCase(repository)

    @Test
    fun `invoke when token exists EXPECT true`() = runTest {
        val token = "test_token_abc123"
        coEvery { repository.getToken() } returns token

        val actual = useCase()

        assertTrue(actual)
    }

    @Test
    fun `invoke when token exists EXPECT invoke get token by repository`() = runTest {
        coEvery { repository.getToken() } returns "test_token_abc123"

        useCase()

        coVerify { repository.getToken() }
    }

    @Test
    fun `invoke when token is null EXPECT false`() = runTest {
        coEvery { repository.getToken() } returns null

        val actual = useCase()

        assertFalse(actual)
    }

    @Test
    fun `invoke when token is null EXPECT invoke get token by repository`() = runTest {
        coEvery { repository.getToken() } returns null

        useCase()

        coVerify { repository.getToken() }
    }

    @Test
    fun `invoke when token is empty string EXPECT true`() = runTest {
        coEvery { repository.getToken() } returns ""

        val actual = useCase()

        assertTrue(actual)
    }
}
