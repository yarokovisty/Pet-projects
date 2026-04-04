package org.yarokovisty.delivery.feature.login.impl.domain.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.yarokovisty.delivery.common.auth.domain.repository.AuthRepository
import org.yarokovisty.delivery.feature.login.api.domain.repository.LoginRepository
import kotlin.test.Test

class SigninUseCaseTest {

    private val loginRepository: LoginRepository = mockk()
    private val authRepository: AuthRepository = mockk()
    private val useCase = SigninUseCase(loginRepository, authRepository)

    private companion object {
        const val TEST_PHONE = "79123456789"
        const val TEST_OTP_CODE = 123456
        const val TEST_TOKEN = "test_token_abc123"
    }

    @Test
    fun `invoke EXPECT get token from login repository`() = runTest {
        coEvery { loginRepository.signin(any(), any()) } returns TEST_TOKEN
        coEvery { authRepository.saveToken(any()) } returns Unit

        useCase(TEST_PHONE, TEST_OTP_CODE)

        coVerify { loginRepository.signin(TEST_PHONE, TEST_OTP_CODE) }
    }

    @Test
    fun `invoke EXPECT save token to auth repository`() = runTest {
        coEvery { loginRepository.signin(any(), any()) } returns TEST_TOKEN
        coEvery { authRepository.saveToken(any()) } returns Unit

        useCase(TEST_PHONE, TEST_OTP_CODE)

        coVerify { authRepository.saveToken(TEST_TOKEN) }
    }

    @Test
    fun `invoke EXPECT invoke signin with phone and otp code`() = runTest {
        coEvery { loginRepository.signin(TEST_PHONE, TEST_OTP_CODE) } returns TEST_TOKEN
        coEvery { authRepository.saveToken(any()) } returns Unit

        useCase(TEST_PHONE, TEST_OTP_CODE)

        coVerify(exactly = 1) {
            loginRepository.signin(TEST_PHONE, TEST_OTP_CODE)
        }
    }

    @Test
    fun `invoke EXPECT token saved matches token from signin response`() = runTest {
        val returnedToken = "specific_token_xyz789"
        coEvery { loginRepository.signin(any(), any()) } returns returnedToken
        coEvery { authRepository.saveToken(any()) } returns Unit

        useCase(TEST_PHONE, TEST_OTP_CODE)

        coVerify { authRepository.saveToken(returnedToken) }
    }
}
