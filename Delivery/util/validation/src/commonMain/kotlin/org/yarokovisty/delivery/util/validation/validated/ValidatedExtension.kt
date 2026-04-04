package org.yarokovisty.delivery.util.validation.validated

inline fun Validated<Unit, Unit>.onValid(body: () -> Unit): Validated<Unit, Unit> =
    apply {
        if (this is Valid) {
            body()
        }
    }

inline fun Validated<Unit, Unit>.onInvalid(body: () -> Unit): Validated<Unit, Unit> =
    apply {
        if (this is Invalid) {
            body()
        }
    }

inline fun<R, V> Validated<R, V>.onValid(body: (V) -> Unit): Validated<R, V> =
    apply {
        if (this is Valid) {
            body(value)
        }
    }

inline fun<R, V> Validated<R, V>.onInvalid(body: (R) -> Unit): Validated<R, V> =
    apply {
        if (this is Invalid) {
            body(reason)
        }
    }

inline fun <R, V, T> Validated<R, V>.fold(
    onInvalid: (R) -> T,
    onValid: (V) -> T
): T =
    when (this) {
        is Invalid -> onInvalid(reason)
        is Valid -> onValid(value)
    }
