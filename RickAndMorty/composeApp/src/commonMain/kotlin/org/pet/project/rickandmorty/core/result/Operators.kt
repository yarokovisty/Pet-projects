package org.pet.project.rickandmorty.core.result

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

// Operators
fun <T> Result<T>.isSuccess(): Boolean {
    return this is Result.Success
}

fun <T> Result<T>.asSuccess():Result.Success<T> {
    return this as Result.Success<T>
}

inline fun <T> Result<T>.onSuccess(block: (T) -> Unit): Result<T> {
    if (this is Result.Success) block(value)
    return this
}

@OptIn(ExperimentalContracts::class)
fun <T> Result<T>.isFailure(): Boolean {
    contract {
        returns(true) implies (this@isFailure is Result.Failure<*>)
    }
    return this is Result.Failure<*>
}

fun <T> Result<T>.asFailure(): Result.Failure<*> {
    return this as Result.Failure<*>
}

inline fun <T> Result<T>.onFailure(block: (Throwable) -> Unit): Result<T> {
    if (this is Result.Failure<*>) block(error)
    return this
}

fun <T> Result<T>.isHttpError(): Boolean {
    return this is Result.Failure.HttpError
}

fun <T> Result<T>.asHttpError(): Result.Failure.HttpError {
    return this as Result.Failure.HttpError
}

inline fun <T, R> Result<T>.map(transform: (value: T) -> R): Result<R> {
    return when(this) {
        is Result.Success -> Result.Success.Value(transform(value))
        is Result.Failure<*> -> this
    }
}

inline fun <T, R> Result<T>.flatMap(transform: (result: Result<T>) -> Result<R>): Result<R> {
    return transform(this)
}