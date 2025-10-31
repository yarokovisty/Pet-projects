package org.pet.project.rickandmorty.core.result

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

// === Type checkers ===
@OptIn(ExperimentalContracts::class)
fun <T> Result<T>.isSuccess(): Boolean {
    contract {
        returns(true) implies (this@isSuccess is Result.Success<T>)
    }
    return this is Result.Success<T>
}

@OptIn(ExperimentalContracts::class)
fun <T> Result<T>.isFailure(): Boolean {
    contract {
        returns(true) implies (this@isFailure is Result.Failure<*>)
    }
    return this is Result.Failure<*>
}

@OptIn(ExperimentalContracts::class)
fun <T> Result<T>.isHttpError(): Boolean {
    contract {
        returns(true) implies (this@isHttpError is Result.Failure.HttpError)
    }
    return this is Result.Failure.HttpError
}


// === Casting helpers ===
fun <T> Result<T>.asSuccess():Result.Success<T> {
    return this as Result.Success<T>
}

fun <T> Result<T>.asFailure(): Result.Failure<*> {
    return this as Result.Failure<*>
}

fun <T> Result<T>.asHttpError(): Result.Failure.HttpError {
    return this as Result.Failure.HttpError
}


// === Chaining ===
inline fun <T> Result<T>.onSuccess(block: (T) -> Unit): Result<T> {
    if (this is Result.Success) block(value)
    return this
}

inline fun <T> Result<T>.onFailure(block: (Throwable) -> Unit): Result<T> {
    if (this is Result.Failure<*>) block(error)
    return this
}


// === Transformations ==
inline fun <T, R> Result<T>.map(transform: (T) -> R): Result<R> {
    return when(this) {
        is Result.Success -> Result.Success.Value(transform(value))
        is Result.Failure<*> -> this
    }
}

suspend inline fun <T, R> Result<T>.asyncMap(transform: suspend (T) -> R): Result<R> {
    return when(this) {
        is Result.Success -> Result.Success.Value(transform(value))
        is Result.Failure<*> -> this
    }
}

inline fun <T, R> Result<T>.flatMap(transform: (result: Result<T>) -> Result<R>): Result<R> {
    return transform(this)
}