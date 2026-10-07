package org.yarokovisty.shift_pizza.component.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.yarokovisty.shift_pizza.util.coroutines.scope.LaunchBuilder
import org.yarokovisty.shift_pizza.util.coroutines.scope.async
import org.yarokovisty.shift_pizza.util.coroutines.scope.launch
import org.yarokovisty.shift_pizza.util.coroutines.scope.launchBuilderFrom

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

    protected fun <T> async(block: suspend CoroutineScope.() -> T): Deferred<T> =
        scope.async(block = block)

    protected fun launchTrying(block: suspend CoroutineScope.() -> Unit): LaunchBuilder =
        scope.launchBuilderFrom(block = block)
}
