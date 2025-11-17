package org.pet.project.rickandmorty.core.network

import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.json.Json
import org.pet.project.rickandmorty.library.result.HttpException
import org.pet.project.rickandmorty.library.result.Result

suspend inline fun <reified T> HttpClient.safeRequest(
    json: Json,
    block: HttpRequestBuilder.() -> Unit
): Result<T> =
    try {
        val response = request { block() }

        if (response.status == HttpStatusCode.OK) {
            val responseBody = response.bodyAsText()
            val data = json.decodeFromString<T>(responseBody)

            Result.Success.HttpResponse(
                value = data,
                statusCode = response.status.value,
                statusMessage = response.status.description,
                url = response.request.url.toString()
            )
        } else {
            val error = HttpException(
                statusCode = response.status.value,
                statusMessage = response.status.description,
                url = response.request.url.toString()
            )
            Result.Failure.HttpError(error)
        }
    } catch (ex: Exception) {
        Result.Failure.Error(ex)
    }