package org.yarokovisty.delivery.core.network.client

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

suspend inline fun <reified T> HttpClient.get(url: String): T =
    get(url).body()

suspend inline fun <reified T, reified R> HttpClient.post(url: String, request: R): T =
    post(url) {
        contentType(ContentType.Application.Json)
        setBody(request)
    }.body()

suspend inline fun <reified T, reified R> HttpClient.patch(
    url: String,
    request: R
): T =
    patch(url) {
        contentType(ContentType.Application.Json)
        setBody(request)
    }.body()

suspend inline fun <reified T, reified R> HttpClient.put(
    url: String,
    request: R
): T =
    put(url) {
        contentType(ContentType.Application.Json)
        setBody(request)
    }.body()
