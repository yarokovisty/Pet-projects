package org.yarokovisty.delivery.util.validation.validated

sealed interface Validated<out R, out V>

@PublishedApi
internal data class Invalid<R>(val reason: R) : Validated<R, Nothing>

@PublishedApi
internal data class Valid<V>(val value: V) : Validated<Nothing, V>
