package org.yarokovisty.delivery.user.domain.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.yarokovisty.delivery.user.domain.repository.UserRepository
import kotlin.test.Test

class SaveUserPhoneNumberUseCaseTest {

    private val repository: UserRepository = mockk()
    private val useCase = SaveUserPhoneNumberUseCase(repository)

    @Test
    fun `invoke with formatted phone number EXPECT save cleared phone number`() = runTest {
        val formattedNumber = "+1 (234) 567-890"
        val clearedNumber = "1234567890"
        coEvery { repository.saveUserPhoneNumber(clearedNumber) } returns Unit

        useCase(formattedNumber)

        coVerify { repository.saveUserPhoneNumber(clearedNumber) }
    }

    @Test
    fun `invoke with phone number containing plus EXPECT save without plus`() = runTest {
        val phoneNumber = "+1234567890"
        val expected = "1234567890"
        coEvery { repository.saveUserPhoneNumber(expected) } returns Unit

        useCase(phoneNumber)

        coVerify { repository.saveUserPhoneNumber(expected) }
    }

    @Test
    fun `invoke with phone number containing parentheses EXPECT save without parentheses`() = runTest {
        val phoneNumber = "(123)4567890"
        val expected = "1234567890"
        coEvery { repository.saveUserPhoneNumber(expected) } returns Unit

        useCase(phoneNumber)

        coVerify { repository.saveUserPhoneNumber(expected) }
    }

    @Test
    fun `invoke with phone number containing spaces EXPECT save without spaces`() = runTest {
        val phoneNumber = "123 456 7890"
        val expected = "1234567890"
        coEvery { repository.saveUserPhoneNumber(expected) } returns Unit

        useCase(phoneNumber)

        coVerify { repository.saveUserPhoneNumber(expected) }
    }

    @Test
    fun `invoke with phone number containing hyphens EXPECT save without hyphens`() = runTest {
        val phoneNumber = "123-456-7890"
        val expected = "1234567890"
        coEvery { repository.saveUserPhoneNumber(expected) } returns Unit

        useCase(phoneNumber)

        coVerify { repository.saveUserPhoneNumber(expected) }
    }

    @Test
    fun `invoke with clean phone number EXPECT save as is`() = runTest {
        val phoneNumber = "1234567890"
        coEvery { repository.saveUserPhoneNumber(phoneNumber) } returns Unit

        useCase(phoneNumber)

        coVerify { repository.saveUserPhoneNumber(phoneNumber) }
    }

    @Test
    fun `invoke with empty phone number EXPECT save empty string`() = runTest {
        val phoneNumber = ""
        coEvery { repository.saveUserPhoneNumber(phoneNumber) } returns Unit

        useCase(phoneNumber)

        coVerify { repository.saveUserPhoneNumber(phoneNumber) }
    }

    @Test
    fun `invoke with complex formatted number EXPECT save only digits`() = runTest {
        val phoneNumber = "+1 (234) 567-890 "
        val expected = "1234567890"
        coEvery { repository.saveUserPhoneNumber(expected) } returns Unit

        useCase(phoneNumber)

        coVerify { repository.saveUserPhoneNumber(expected) }
    }
}
