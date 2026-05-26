package org.yarokovisty.delivery.common.profile.main.domain.usecase

import org.yarokovisty.delivery.common.auth.domain.repository.AuthRepository
import org.yarokovisty.delivery.common.profile.main.domain.entity.User
import org.yarokovisty.delivery.common.profile.main.domain.repository.UserRepository

class UpdateUserUseCase(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
) {

    suspend operator fun invoke(user: User) {
        val token = authRepository.getToken() ?: error("Token is null")
        userRepository.update(user, token)
    }
}
