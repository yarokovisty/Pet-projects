package org.pet.project.rickandmorty.common.data

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import org.pet.project.rickandmorty.library.result.EmptyResult
import org.pet.project.rickandmorty.library.result.Result
import org.pet.project.rickandmorty.library.result.onSuccess

abstract class Paginator<Key, Item>(initialKey: Key) {

    private var currentKey = initialKey
    private var isMakingRequest = false
    private var isEndReached = false

    protected abstract suspend fun fetchData(page: Key): Result<Item>

    protected abstract fun getNextKey(currentKey: Key, result: Item): Key

    protected abstract fun checkEndReached(currentKey: Key, result: Item): Boolean

    private val _paginationFlow = MutableSharedFlow<Result<Item>>()
    val paginationFlow: SharedFlow<Result<Item>> = _paginationFlow

    // TODO исправить реализацию (использовать Mutex)
    // TODO изменить на состояния
    suspend fun loadItems() {
        if (isMakingRequest || isEndReached) return

        isMakingRequest = true

        val result = fetchData(currentKey)
        isMakingRequest = false

        result.onSuccess { item ->
            currentKey = getNextKey(currentKey, item)
            isEndReached = checkEndReached(currentKey, item)
        }

        _paginationFlow.emit(result)

        if (isEndReached) {
            _paginationFlow.emit(EmptyResult)
        }
    }

}