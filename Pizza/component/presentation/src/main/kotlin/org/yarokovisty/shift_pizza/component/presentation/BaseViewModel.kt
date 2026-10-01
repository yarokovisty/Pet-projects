package org.yarokovisty.shift_pizza.component.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

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
}
