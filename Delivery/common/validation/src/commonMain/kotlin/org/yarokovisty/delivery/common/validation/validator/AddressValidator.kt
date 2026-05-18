package org.yarokovisty.delivery.common.validation.validator

import org.yarokovisty.delivery.common.validation.error.AddressValidationError
import org.yarokovisty.delivery.util.validation.validate.Validate
import org.yarokovisty.delivery.util.validation.validated.Validated

class AddressValidator {

    fun validate(address: String): Validated<AddressValidationError, String> =
        create().invoke(address)

    private fun create(): Validate<AddressValidationError, String> =
        isNotBlankValidator(AddressValidationError.EMPTY)
}
