package org.yarokovisty.delivery.core.network.client

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class HttpService(
    @PublishedApi
    internal val client: HttpClient
) {

    suspend inline fun <reified T> get(url: String): T =
        client.get(url).body()
}
