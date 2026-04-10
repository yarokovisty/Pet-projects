package org.yarokovisty.delivery.core.network.client

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType

suspend inline fun <reified T> HttpClient.get(url: String): T =
    get(url).body()

suspend inline fun <reified T> HttpClient.get(url: String, token: String): T =
    get(url) {
        withToken(token)
    }.body()

suspend inline fun <reified T, reified R> HttpClient.post(url: String, request: R): T =
    post(url) {
        contentType(ContentType.Application.Json)
        setBody(request)
    }.body()

suspend inline fun <reified T, reified R> HttpClient.post(
    url: String,
    request: R,
    token: String
): T =
    post(url) {
        contentType(ContentType.Application.Json)
        setBody(request)
        withToken(token)
    }.body()

suspend inline fun <reified T, reified R> HttpClient.patch(
    url: String,
    request: R
): T =
    patch(url) {
        contentType(ContentType.Application.Json)
        setBody(request)
    }.body()

suspend inline fun <reified T, reified R> HttpClient.patch(
    url: String,
    request: R,
    token: String
): T =
    patch(url) {
        contentType(ContentType.Application.Json)
        setBody(request)
        withToken(token)
    }.body()

fun HttpRequestBuilder.withToken(token: String) {
    header(HttpHeaders.Authorization, "Bearer $token")
}
