package org.yarokovisty.delivery.common.profile.main.domain.usecase

import org.yarokovisty.delivery.common.auth.domain.repository.AuthRepository
import org.yarokovisty.delivery.common.profile.main.domain.entity.User
import org.yarokovisty.delivery.common.profile.main.domain.repository.UserRepository

class GetUserUseCase(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
) {

    suspend operator fun invoke(): User? =
        userRepository.getUserFromLocal() ?: getUserFromNetwork()

    private suspend fun getUserFromNetwork(): User? {
        val token = authRepository.getToken() ?: return null
        return userRepository.getUserFromNetwork(token)
    }
}
