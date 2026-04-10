package org.yarokovisty.delivery.feature.profile.main.impl.domain.usecase

import org.yarokovisty.delivery.common.auth.domain.repository.AuthRepository
import org.yarokovisty.delivery.feature.profile.main.api.domain.entity.User
import org.yarokovisty.delivery.feature.profile.main.api.domain.repository.UserRepository

internal class UpdateUserUseCase(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
) {

    suspend operator fun invoke(user: User) {
        val token = authRepository.getToken() ?: error("Token is null")
        userRepository.updateUser(user, token)
    }
}
