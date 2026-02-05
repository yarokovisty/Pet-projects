package org.yarokovisty.delivery.util.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LaunchBuilder internal constructor(
    val coroutineScope: CoroutineScope,
    val dispatcher: CoroutineDispatcher,
    val body: suspend CoroutineScope.() -> Unit
) {

    inline infix fun handle(crossinline handler: (exception: Exception) -> Unit): Job {
        val coroutineExceptionHandler = DeliveryCoroutineExceptionHandler { _, e -> handler(e) }
        val coroutineContext = dispatcher + coroutineExceptionHandler

        return coroutineScope.launch(
            context = coroutineContext,
            block = body
        )
    }
}
