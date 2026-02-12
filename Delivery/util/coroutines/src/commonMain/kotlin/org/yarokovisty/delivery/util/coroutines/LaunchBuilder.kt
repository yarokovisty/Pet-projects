package org.yarokovisty.delivery.util.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LaunchBuilder internal constructor(
    val coroutineScope: CoroutineScope,
    val body: suspend CoroutineScope.() -> Unit
) {

    inline infix fun handle(crossinline handler: (exception: Exception) -> Unit): Job {
        val coroutineExceptionHandler = DeliveryCoroutineExceptionHandler { _, e -> handler(e) }

        return coroutineScope.launch(
            context = coroutineExceptionHandler,
            block = body
        )
    }
}
