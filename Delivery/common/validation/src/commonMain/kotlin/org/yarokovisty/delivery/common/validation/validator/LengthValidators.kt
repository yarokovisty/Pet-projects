package org.yarokovisty.delivery.common.validation.validator

import org.yarokovisty.delivery.util.validation.validate.Validate
import org.yarokovisty.delivery.util.validation.validate.validate

fun <R> isNotBlankValidator(reason: R): Validate<R, String> =
    validate(reason) {
        it.isNotBlank()
    }

fun <R> hasEqualsLengthValidator(reason: R, length: Int): Validate<R, String> =
    validate(reason) {
        it.length == length
    }
