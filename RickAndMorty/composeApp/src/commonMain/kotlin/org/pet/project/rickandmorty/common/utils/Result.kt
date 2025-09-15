package org.pet.project.rickandmorty.common.utils

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

sealed class Result<out T> {

    sealed class Success<T> : Result<T>() {

        abstract val value: T

        override fun toString(): String = "Success($value)"

        class Value<T>(override val value: T) : Success<T>()

        data class HttpResponse<T>(
            override val value: T,
            override val statusCode: Int,
            override val statusMessage: String? = null,
            override val url: String? = null
        ) : Success<T>(), IHttpResponse

        object Empty : Success<Nothing>() {

            override val value: Nothing get() = error("No value")

            override fun toString() = "Success"
        }
    }

    sealed class Failure<E : Throwable>(open val error: E) : Result<Nothing>() {

        override fun toString() = "Failure($error)"

        class Error(override val error: Throwable) : Failure<Throwable>(error)

        class HttpError(override val error: HttpException) : Failure<HttpException>(error), IHttpResponse {

            override val statusCode: Int get() = error.statusCode

            override val statusMessage: String? get() = error.statusMessage

            override val url: String? get() = error.url
        }
    }
}

typealias EmptyResult = Result.Success.Empty


// HTTP
class HttpException(
    val statusCode: Int,
    val statusMessage: String? = null,
    val url: String? = null,
    cause: Throwable? = null
) : Exception(null, cause)


interface IHttpResponse {

    val statusCode: Int

    val statusMessage: String?

    val url: String?
}


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



