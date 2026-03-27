package org.yarokovisty.delivery.user.domain.usecase

import org.yarokovisty.delivery.user.domain.repository.UserRepository

class IsUserAuthorizedUseCase(private val repository: UserRepository) {

    suspend operator fun invoke(): Boolean =
        repository.getAuthPhoneNumber() != null
}
