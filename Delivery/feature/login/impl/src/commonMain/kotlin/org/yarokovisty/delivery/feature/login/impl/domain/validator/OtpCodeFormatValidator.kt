package org.yarokovisty.delivery.feature.login.impl.domain.validator

import org.yarokovisty.delivery.common.validation.validator.hasEqualsLengthValidator
import org.yarokovisty.delivery.common.validation.validator.isNotBlankValidator
import org.yarokovisty.delivery.util.validation.validate.Validate
import org.yarokovisty.delivery.util.validation.validate.plus
import org.yarokovisty.delivery.util.validation.validated.Validated

internal class OtpCodeFormatValidator {

    private companion object {
        const val OTP_CODE_LENGTH = 6
    }

    fun validate(code: String): Validated<OtpCodeFormatValidationError, String> =
        create().invoke(code)

    private fun create(): Validate<OtpCodeFormatValidationError, String> =
        isNotBlankValidator(OtpCodeFormatValidationError.EMPTY) +
            hasEqualsLengthValidator(OtpCodeFormatValidationError.INVALID_LENGTH, OTP_CODE_LENGTH)
}
