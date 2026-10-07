package org.yarokovisty.shift_pizza.util.coroutines.scope

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

@Suppress("FunctionName")
inline fun AppCoroutineExceptionHandler(
    crossinline handler: (CoroutineContext, Exception) -> Unit
): CoroutineExceptionHandler =
    CoroutineExceptionHandler { context, throwable ->
        if (throwable is Exception) {
            handler(context, throwable)
        } else {
            throw throwable
        }
    }
