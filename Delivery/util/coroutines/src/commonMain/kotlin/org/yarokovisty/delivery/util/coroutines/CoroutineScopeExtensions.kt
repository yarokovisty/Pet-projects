package org.yarokovisty.delivery.util.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

inline fun CoroutineScope.launch(
    crossinline onError: (exception: Exception) -> Unit = {},
    noinline block: suspend CoroutineScope.() -> Unit
): Job {
    val coroutineExceptionHandler = DeliveryCoroutineExceptionHandler { _, exception -> onError(exception) }

    return launch(context = coroutineExceptionHandler, block = block)
}

inline fun <T> CoroutineScope.async(
    crossinline onError: (exception: Exception) -> Unit = {},
    noinline block: suspend CoroutineScope.() -> T
): Deferred<T> {
    val coroutineExceptionHandler = DeliveryCoroutineExceptionHandler { _, exception -> onError(exception) }

    return async(context = coroutineExceptionHandler, block = block)
}

fun CoroutineScope.launchBuilderFrom(
    block: suspend CoroutineScope.() -> Unit
): LaunchBuilder =
    LaunchBuilder(this, block)
