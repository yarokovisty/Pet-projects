package org.yarokovisty.delivery.util.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

inline fun CoroutineScope.launch(
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
    crossinline onError: (exception: Exception) -> Unit = {},
    noinline block: suspend CoroutineScope.() -> Unit
): Job {
    val coroutineExceptionHandler = DeliveryCoroutineExceptionHandler { _, exception -> onError(exception) }
    val coroutineContext = dispatcher + coroutineExceptionHandler

    return launch(context = coroutineContext, block = block)
}

inline fun <T> CoroutineScope.async(
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
    crossinline onError: (exception: Exception) -> Unit = {},
    noinline block: suspend CoroutineScope.() -> T
): Deferred<T> {
    val coroutineExceptionHandler = DeliveryCoroutineExceptionHandler { _, exception -> onError(exception) }
    val coroutineContext = dispatcher + coroutineExceptionHandler

    return async(context = coroutineContext, block = block)
}

fun CoroutineScope.launchBuilderFrom(
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
    block: suspend CoroutineScope.() -> Unit
): LaunchBuilder =
    LaunchBuilder(this, dispatcher, block)
