package org.yarokovisty.delivery.util.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

inline fun CoroutineScope.launch(
    crossinline onError: (throwable: Throwable) -> Unit = {},
    noinline block: suspend CoroutineScope.() -> Unit
): Job {
    val coroutineExceptionHandler = DeliveryCoroutineExceptionHandler { _, throwable -> onError(throwable) }

    return launch(context = coroutineExceptionHandler, block = block)
}

inline fun <T> CoroutineScope.async(
    crossinline onError: (throwable: Throwable) -> Unit = {},
    noinline block: suspend CoroutineScope.() -> T
): Deferred<T> {
    val coroutineExceptionHandler = DeliveryCoroutineExceptionHandler { _, throwable -> onError(throwable) }

    return async(context = coroutineExceptionHandler, block = block)
}

fun CoroutineScope.launchBuilderFrom(
    block: suspend CoroutineScope.() -> Unit
): LaunchBuilder =
    LaunchBuilder(this, block)
