package org.yarokovisty.delivery.core.network.client

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

suspend inline fun <reified T> HttpClient.get(url: String): T =
    get(url).body()
