package org.yarokovisty.delivery.common.validation.validator

import org.yarokovisty.delivery.common.validation.error.EmailValidationError
import org.yarokovisty.delivery.util.validation.validate.Validate
import org.yarokovisty.delivery.util.validation.validate.validate

private val EMAIL_REGEX =
    "^[_A-Za-z0-9]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)$".toRegex()

internal fun hasEmailFormatValidator(): Validate<EmailValidationError, String> =
    validate(EmailValidationError.NOT_MATCH_THE_PATTERN) { email ->
        EMAIL_REGEX.matches(email)
    }
