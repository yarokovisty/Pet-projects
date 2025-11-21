package org.pet.project.rickandmorty.common.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.pet.project.rickandmorty.library.result.Result
import org.pet.project.rickandmorty.library.result.onFailure
import org.pet.project.rickandmorty.library.result.onSuccess

abstract class Paginator<Key, Item>(initialKey: Key) {

    private val mutex = Mutex()
    private var currentKey = initialKey

    protected abstract suspend fun fetchData(page: Key): Result<Item>

    protected abstract fun getNextKey(currentKey: Key, result: Item): Key

    protected abstract fun checkEndReached(currentKey: Key, result: Item): Boolean

    private val _paginationFlow = MutableStateFlow<PaginatorState<Item>>(PaginatorState.Initial)
    val paginationFlow: StateFlow<PaginatorState<Item>> = _paginationFlow

    suspend fun loadItems(): Unit = mutex.withLock {
        val currentState = _paginationFlow.value
        if (currentState is PaginatorState.Loading || currentState is PaginatorState.End) return

        _paginationFlow.value = PaginatorState.Loading

        val result = fetchData(currentKey)

        result.onSuccess { item ->
            currentKey = getNextKey(currentKey, item)
            _paginationFlow.value = PaginatorState.Success(item)

            if (checkEndReached(currentKey, item)) {
                _paginationFlow.value = PaginatorState.End
            }
        }.onFailure { t ->
            _paginationFlow.update { PaginatorState.Error(t) }
        }
    }
}

sealed interface PaginatorState<out T> {
    object Initial : PaginatorState<Nothing>
    class Success<T>(val value: T) : PaginatorState<T>
    class Error(val throwable: Throwable) : PaginatorState<Nothing>
    object Loading : PaginatorState<Nothing>
    object End : PaginatorState<Nothing>
}