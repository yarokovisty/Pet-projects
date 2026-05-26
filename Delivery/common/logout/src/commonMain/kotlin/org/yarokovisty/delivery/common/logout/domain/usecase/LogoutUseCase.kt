package org.yarokovisty.delivery.common.logout.domain.usecase

import org.yarokovisty.delivery.common.auth.domain.repository.AuthRepository
import org.yarokovisty.delivery.common.profile.main.domain.repository.UserRepository

class LogoutUseCase(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
) {

    suspend operator fun invoke() {
        authRepository.clearToken()
        userRepository.clear()
    }
}
