package org.yarokovisty.delivery.feature.login.impl.domain.usecase

import org.yarokovisty.delivery.common.auth.domain.repository.AuthRepository
import org.yarokovisty.delivery.feature.login.api.domain.repository.LoginRepository

internal class SigninUseCase(
    private val loginRepository: LoginRepository,
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(phoneNumber: String, otpCode: Int) {
        val token = loginRepository.signin(phoneNumber, otpCode)
        authRepository.saveToken(token)
    }
}
