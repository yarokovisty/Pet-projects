package org.yarokovisty.delivery.feature.login.domain.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.yarokovisty.delivery.common.auth.domain.repository.AuthRepository
import org.yarokovisty.delivery.common.profile.main.domain.entity.User
import org.yarokovisty.delivery.common.profile.main.domain.repository.UserRepository
import org.yarokovisty.delivery.feature.login.domain.repository.LoginRepository
import kotlin.test.Test

class SigninUseCaseTest {

    private val authRepository: AuthRepository = mockk()
    private val loginRepository: LoginRepository = mockk()
    private val userRepository: UserRepository = mockk()
    private val useCase = SigninUseCase(authRepository, loginRepository, userRepository)

    private companion object {
        const val TEST_PHONE = "79123456789"
        const val TEST_OTP_CODE = 123456
        const val TEST_TOKEN = "test_token_abc123"
    }

    private val testUser = User(
        id = "user123",
        phone = TEST_PHONE,
        firstname = "Ivan",
        lastname = null,
        middlename = null,
        email = null,
        city = null
    )

    @Test
    fun `invoke EXPECT signin called on login repository`() = runTest {
        coEvery { loginRepository.signin(any(), any()) } returns (TEST_TOKEN to testUser)
        coEvery { authRepository.saveToken(any()) } returns Unit
        coEvery { userRepository.set(any()) } returns Unit

        useCase(TEST_PHONE, TEST_OTP_CODE)

        coVerify { loginRepository.signin(TEST_PHONE, TEST_OTP_CODE) }
    }

    @Test
    fun `invoke EXPECT save token to auth repository`() = runTest {
        coEvery { loginRepository.signin(any(), any()) } returns (TEST_TOKEN to testUser)
        coEvery { authRepository.saveToken(any()) } returns Unit
        coEvery { userRepository.set(any()) } returns Unit

        useCase(TEST_PHONE, TEST_OTP_CODE)

        coVerify { authRepository.saveToken(TEST_TOKEN) }
    }

    @Test
    fun `invoke EXPECT user saved to user repository`() = runTest {
        coEvery { loginRepository.signin(any(), any()) } returns (TEST_TOKEN to testUser)
        coEvery { authRepository.saveToken(any()) } returns Unit
        coEvery { userRepository.set(any()) } returns Unit

        useCase(TEST_PHONE, TEST_OTP_CODE)

        coVerify { userRepository.set(testUser) }
    }

    @Test
    fun `invoke EXPECT token saved matches token from signin response`() = runTest {
        val returnedToken = "specific_token_xyz789"
        coEvery { loginRepository.signin(any(), any()) } returns (returnedToken to testUser)
        coEvery { authRepository.saveToken(any()) } returns Unit
        coEvery { userRepository.set(any()) } returns Unit

        useCase(TEST_PHONE, TEST_OTP_CODE)

        coVerify { authRepository.saveToken(returnedToken) }
    }
}
