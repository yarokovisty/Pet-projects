package org.yarokovisty.delivery.libs.coordinator

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

object Coordinator {

    private val _result = MutableSharedFlow<CoordinatorResult<*>>()
    val result = _result.asSharedFlow()

    suspend fun <T> publish(key: String, value: T) {
        val coordinatorResult = CoordinatorResult(key, value)
        _result.emit(coordinatorResult)
    }

    suspend fun <T> await(key: String): T =
        result
            .filter { key == it.key }
            .filterIsInstance<CoordinatorResult<T>>()
            .map { it.value }
            .first()
}
