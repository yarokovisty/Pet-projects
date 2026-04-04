package org.yarokovisty.delivery.util.coroutines

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

@Suppress("FunctionName")
inline fun DeliveryCoroutineExceptionHandler(
    crossinline handler: (context: CoroutineContext, throwable: Throwable) -> Unit
): CoroutineExceptionHandler =
    CoroutineExceptionHandler { context, throwable ->
        handler(context, throwable)
    }
