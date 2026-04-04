package org.yarokovisty.delivery.common.auth.domain.usecase

import org.yarokovisty.delivery.common.auth.domain.repository.AuthRepository

class IsUserAuthorizedUseCase(private val repo: AuthRepository) {

    suspend operator fun invoke(): Boolean =
        repo.getToken() != null
}
