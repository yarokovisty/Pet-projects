package org.yarokovisty.delivery.libs.coordinator

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.first

object Coordinator {

    private val _result = MutableSharedFlow<Any>()
    val result = _result.asSharedFlow()

    suspend fun <T : Any> publish(value: T) {
        _result.emit(value)
    }

    suspend inline fun <reified T> await(): T =
        result
            .filterIsInstance<T>()
            .first()
}
