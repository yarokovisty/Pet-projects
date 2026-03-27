package org.yarokovisty.delivery.user.domain.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.yarokovisty.delivery.user.domain.repository.UserRepository
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class IsUserAuthorizedUseCaseTest {

    private val repository: UserRepository = mockk()
    private val useCase = IsUserAuthorizedUseCase(repository)

    @Test
    fun `invoke when phone number exists EXPECT true`() = runTest {
        val phoneNumber = "+1234567890"
        coEvery { repository.getAuthPhoneNumber() } returns phoneNumber

        val actual = useCase()

        assertTrue(actual)
    }

    @Test
    fun `invoke when phone number exists EXPECT invoke get auth phone number`() = runTest {
        coEvery { repository.getAuthPhoneNumber() } returns "+1234567890"

        useCase()

        coVerify { repository.getAuthPhoneNumber() }
    }

    @Test
    fun `invoke when phone number is null EXPECT false`() = runTest {
        coEvery { repository.getAuthPhoneNumber() } returns null

        val actual = useCase()

        assertFalse(actual)
    }

    @Test
    fun `invoke when phone number is null EXPECT invoke get auth phone number`() = runTest {
        coEvery { repository.getAuthPhoneNumber() } returns null

        useCase()

        coVerify { repository.getAuthPhoneNumber() }
    }

    @Test
    fun `invoke when phone number is empty string EXPECT true`() = runTest {
        coEvery { repository.getAuthPhoneNumber() } returns ""

        val actual = useCase()

        assertTrue(actual)
    }
}
