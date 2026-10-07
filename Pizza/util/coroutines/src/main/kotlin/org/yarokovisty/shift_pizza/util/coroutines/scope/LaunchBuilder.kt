package org.yarokovisty.shift_pizza.util.coroutines.scope

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LaunchBuilder internal constructor(
    val scope: CoroutineScope,
    val body: suspend CoroutineScope.() -> Unit
) {

    inline infix fun handle(crossinline handler: (Exception) -> Unit): Job =
        scope.launch(
            context = AppCoroutineExceptionHandler { _, e -> handler(e) },
            block = body
        )
}
