package org.yarokovisty.delivery.core.common.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.yarokovisty.delivery.libs.coordinator.Coordinator
import org.yarokovisty.delivery.util.coroutines.LaunchBuilder
import org.yarokovisty.delivery.util.coroutines.launch
import org.yarokovisty.delivery.util.coroutines.launchBuilderFrom

abstract class BaseViewModel<S : State, I : Intent, E : Event>(initialState: S) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    protected val stateValue: S
        get() = _state.value

    private val _events = MutableSharedFlow<E>()
    val events = _events.asSharedFlow()

    protected val scope = viewModelScope

    protected fun updateState(block: S.() -> S) {
        _state.update(block)
    }

    abstract fun onIntent(intent: I)

    protected suspend fun emitEvent(event: E) {
        _events.emit(event)
    }

    protected fun launch(block: suspend CoroutineScope.() -> Unit): Job =
        scope.launch(block = block)

    protected fun launchTrying(block: suspend CoroutineScope.() -> Unit): LaunchBuilder =
        scope.launchBuilderFrom(block = block)

    protected suspend fun <T : Any> publishResult(result: T) {
        Coordinator.publish(result)
    }

    protected suspend inline fun <reified T> awaitResult(): T =
        Coordinator.await()
}
