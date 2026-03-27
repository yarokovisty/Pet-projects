package org.yarokovisty.delivery.user.domain.usecase

import org.yarokovisty.delivery.user.domain.repository.UserRepository

class RemoveUserPhoneNumberUseCase(
    private val repository: UserRepository
) : suspend () -> Unit by repository::removeUserPhoneNumber
