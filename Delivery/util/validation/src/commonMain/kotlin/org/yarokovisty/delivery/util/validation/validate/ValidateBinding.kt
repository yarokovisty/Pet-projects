package org.yarokovisty.delivery.util.validation.validate

import org.yarokovisty.delivery.util.validation.validated.Invalid
import org.yarokovisty.delivery.util.validation.validated.Valid

operator fun <R, V> Validate<R, V>.plus(
    other: Validate<R, V>
): Validate<R, V> =
    Validate { value ->
        when (val validated = this(value)) {
            is Invalid -> validated
            is Valid -> other(value)
        }
    }
