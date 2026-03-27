package org.yarokovisty.delivery.user.domain.usecase

import org.yarokovisty.delivery.user.domain.repository.UserRepository

class SaveUserPhoneNumberUseCase(
    private val repository: UserRepository
) {

    suspend operator fun invoke(phoneNumber: String) {
        val clearPhoneNumber = clearPhoneNumber(phoneNumber)
        repository.saveUserPhoneNumber(clearPhoneNumber)
    }

    private fun clearPhoneNumber(value: String): String {
        val regex = Regex("[+() -]")
        return value.replace(regex, "")
    }
}
