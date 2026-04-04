package org.yarokovisty.delivery.feature.login.impl.domain.usecase

import org.yarokovisty.delivery.common.validation.error.PhoneValidationError
import org.yarokovisty.delivery.common.validation.validator.PhoneValidator
import org.yarokovisty.delivery.util.validation.validated.Validated

internal class RuPhoneValidationUseCase(
    private val phoneValidator: PhoneValidator
) {

    private companion object {
        const val RU_PHONE_LENGTH = 11
    }

    operator fun invoke(phoneNumber: String): Validated<PhoneValidationError, String> =
        phoneValidator.validate(phoneNumber, RU_PHONE_LENGTH)
}
