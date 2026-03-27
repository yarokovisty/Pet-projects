package org.yarokovisty.delivery.util.validation.validate

import org.yarokovisty.delivery.util.validation.validated.Validated

fun interface Validate<out R, V> : (V) -> Validated<R, V> {

    override fun invoke(value: V): Validated<R, V>
}
