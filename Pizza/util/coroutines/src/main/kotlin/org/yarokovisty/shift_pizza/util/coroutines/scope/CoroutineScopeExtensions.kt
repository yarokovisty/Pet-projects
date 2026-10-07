package org.yarokovisty.shift_pizza.util.coroutines.scope

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

inline fun CoroutineScope.launch(
    crossinline onError: (Exception) -> Unit = {},
    noinline block: suspend CoroutineScope.() -> Unit
): Job =
    launch(
        context = AppCoroutineExceptionHandler { _, e -> onError(e) },
        block = block
    )

inline fun <T> CoroutineScope.async(
    crossinline onError: (throwable: Throwable) -> Unit = {},
    noinline block: suspend CoroutineScope.() -> T
): Deferred<T> =
    async(
        context = AppCoroutineExceptionHandler { _, e -> onError(e) },
        block = block
    )

fun CoroutineScope.launchBuilderFrom(
    block: suspend CoroutineScope.() -> Unit
): LaunchBuilder =
    LaunchBuilder(this, block)
