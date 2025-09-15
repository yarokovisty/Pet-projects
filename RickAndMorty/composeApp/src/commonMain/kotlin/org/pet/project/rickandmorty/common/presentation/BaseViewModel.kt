package org.pet.project.rickandmorty.common.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.getValue

abstract class BaseViewModel<S : State, I : Intent, E>() : ViewModel() {

    private val _state by lazy { MutableStateFlow(initState()) }
    val state: StateFlow<S> by lazy { _state.asStateFlow() }

    protected val currentState: S get() = _state.value

    protected abstract fun initState(): S

    protected fun updateState(update: S.() -> S) {
        _state.update { _state.value.update() }
    }

    abstract fun onIntent(intent: I)

    private val _event by lazy { MutableSharedFlow<E>() }
    val event: SharedFlow<E> = _event

    protected fun setEvent(event: E) {
        _event.tryEmit(event)
    }
}

