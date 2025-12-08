package org.pet.project.rickandmorty.common.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<S : State, I : Intent, E : Event>() : ViewModel() {

    protected val scope = viewModelScope

    protected fun launchInScope(
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return scope.launch(
            context = dispatcher,
            block = block
        )
    }

    private val _state by lazy { MutableStateFlow(initState()) }
    val state: StateFlow<S> by lazy { _state.asStateFlow() }

    protected val stateValue: S get() = _state.value

    protected abstract fun initState(): S

    protected fun updateState(update: S.() -> S) {
        _state.update { _state.value.update() }
    }

    abstract fun onIntent(intent: I)

    private val _event by lazy { MutableSharedFlow<E>() }
    val event: SharedFlow<E> = _event

    protected suspend fun setEvent(event: E) {
        _event.emit(event)
    }
}

