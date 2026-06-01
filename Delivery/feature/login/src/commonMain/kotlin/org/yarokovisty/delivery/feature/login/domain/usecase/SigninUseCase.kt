package org.yarokovisty.delivery.feature.login.domain.usecase

import org.yarokovisty.delivery.common.auth.domain.repository.AuthRepository
import org.yarokovisty.delivery.common.profile.main.domain.repository.UserRepository
import org.yarokovisty.delivery.feature.login.domain.repository.LoginRepository

internal class SigninUseCase(
    private val authRepository: AuthRepository,
    private val loginRepository: LoginRepository,
    private val userRepository: UserRepository,
) {

    suspend operator fun invoke(phoneNumber: String, otpCode: Int) {
        val (token, user) = loginRepository.signin(phoneNumber, otpCode)
        authRepository.saveToken(token)
        userRepository.set(user)
    }
}
