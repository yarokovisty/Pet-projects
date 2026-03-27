package org.yarokovisty.delivery.common.validation.validator

import org.yarokovisty.delivery.common.validation.error.PhoneValidationError
import org.yarokovisty.delivery.util.validation.validate.Validate
import org.yarokovisty.delivery.util.validation.validate.plus
import org.yarokovisty.delivery.util.validation.validated.Validated

class PhoneValidator {

    fun validate(phone: String, length: Int): Validated<PhoneValidationError, String> =
        create(length).invoke(phone)

    private fun create(phoneLength: Int): Validate<PhoneValidationError, String> =
        isNotBlankValidator(PhoneValidationError.EMPTY) +
            hasEqualsLengthValidator(PhoneValidationError.INVALID_LENGTH, phoneLength)
}
