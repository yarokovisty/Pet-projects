package org.yarokovisty.delivery.common.validation.validator

import org.yarokovisty.delivery.common.validation.error.EmailValidationError
import org.yarokovisty.delivery.util.validation.validate.Validate
import org.yarokovisty.delivery.util.validation.validate.plus
import org.yarokovisty.delivery.util.validation.validated.Validated
import org.yarokovisty.delivery.util.validation.validated.valid

class EmailValidator {

    fun validate(email: String, required: Boolean = false): Validated<EmailValidationError, String> =
        if (required && email.isEmpty()) {
            valid(email)
        } else {
            create().invoke(email)
        }

    private fun create(): Validate<EmailValidationError, String> =
        isNotBlankValidator(EmailValidationError.EMPTY) +
            hasEmailFormatValidator()
}
