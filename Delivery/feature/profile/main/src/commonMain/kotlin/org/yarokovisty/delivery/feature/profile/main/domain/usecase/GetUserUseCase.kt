package org.yarokovisty.delivery.feature.profile.main.domain.usecase

import org.yarokovisty.delivery.common.auth.domain.repository.AuthRepository
import org.yarokovisty.delivery.feature.profile.main.domain.entity.User
import org.yarokovisty.delivery.feature.profile.main.domain.repository.UserRepository

internal class GetUserUseCase(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
) {

    suspend operator fun invoke(): User {
        val token = authRepository.getToken() ?: error("Token is missing")
        return userRepository.getUser(token)
    }
}
