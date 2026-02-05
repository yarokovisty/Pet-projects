package org.yarokovisty.delivery.util.coroutines

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

@Suppress("FunctionName")
inline fun DeliveryCoroutineExceptionHandler(
    crossinline handler: (context: CoroutineContext, exception: Exception) -> Unit
): CoroutineExceptionHandler =
    CoroutineExceptionHandler { context, throwable ->
        if (throwable is Exception) {
            handler(context, throwable)
        } else {
            throw throwable
        }
    }
