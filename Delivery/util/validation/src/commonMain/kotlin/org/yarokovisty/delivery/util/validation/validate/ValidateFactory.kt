package org.yarokovisty.delivery.util.validation.validate

import org.yarokovisty.delivery.util.validation.validated.Invalid
import org.yarokovisty.delivery.util.validation.validated.Valid

inline fun <R, V> validate(
    reason: R,
    crossinline predicate: (value: V) -> Boolean
): Validate<R, V> =
    Validate {
        if (predicate(it)) {
            Valid(it)
        } else {
            Invalid(reason)
        }
    }
