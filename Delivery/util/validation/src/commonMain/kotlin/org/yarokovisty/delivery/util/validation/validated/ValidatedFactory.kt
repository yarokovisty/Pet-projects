package org.yarokovisty.delivery.util.validation.validated

fun <R> invalid(reason: R): Validated<R, Nothing> =
    Invalid(reason)

fun <V> valid(value: V): Validated<Nothing, V> =
    Valid(value)
