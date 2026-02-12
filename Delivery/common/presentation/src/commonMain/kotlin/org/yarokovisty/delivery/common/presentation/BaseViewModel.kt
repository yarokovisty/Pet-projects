package org.yarokovisty.delivery.common.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.yarokovisty.delivery.util.coroutines.LaunchBuilder
import org.yarokovisty.delivery.util.coroutines.launch
import org.yarokovisty.delivery.util.coroutines.launchBuilderFrom

abstract class BaseViewModel<S : State, I : Intent, E : Event> : ViewModel() {

    private val _state = MutableStateFlow(initState())
    val state = _state.asStateFlow()

    protected val stateValue: S
        get() = _state.value

    private val _events = MutableSharedFlow<E>()
    val events = _events.asSharedFlow()

    protected val scope: CoroutineScope
        get() = viewModelScope

    protected abstract fun initState(): S

    protected fun updateState(block: S.() -> S) {
        _state.update(block)
    }

    abstract fun onIntent(intent: I)

    protected suspend fun emitEvent(event: E) {
        _events.emit(event)
    }

    protected fun launch(
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
        block: suspend CoroutineScope.() -> Unit
    ): Job =
        scope.launch(dispatcher = dispatcher, block = block)

    protected fun launchTrying(
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
        block: suspend CoroutineScope.() -> Unit
    ): LaunchBuilder =
        scope.launchBuilderFrom(dispatcher = dispatcher, block = block)
}
