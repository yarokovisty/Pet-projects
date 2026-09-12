package org.yarokovisty.shift_pizza.core.network.ktor

import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private const val BASE_URL = "https://juniorsbootcamp.ru"
private const val CONNECT_TIMEOUT = 5_000L
private const val REQUEST_TIMEOUT = 10_000L

internal fun HttpClientConfig<*>.setConfig(json: Json) {

    defaultRequest {
        url(BASE_URL)
    }

    install(HttpTimeout) {
        requestTimeoutMillis = REQUEST_TIMEOUT
        connectTimeoutMillis = CONNECT_TIMEOUT
    }

    install(ContentNegotiation) {
        json(json)
    }
}

