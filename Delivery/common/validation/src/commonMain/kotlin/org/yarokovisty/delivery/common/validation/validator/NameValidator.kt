package org.yarokovisty.delivery.common.validation.validator

import org.yarokovisty.delivery.common.validation.error.NameValidationError
import org.yarokovisty.delivery.util.validation.validate.Validate
import org.yarokovisty.delivery.util.validation.validated.Validated

class NameValidator {

    fun validate(name: String): Validated<NameValidationError, String> =
        create().invoke(name)

    private fun create(): Validate<NameValidationError, String> =
        isNotBlankValidator(NameValidationError.EMPTY)
}
