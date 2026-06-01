package org.yarokovisty.delivery.core.network.client

import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json

@Suppress("FunctionName")
internal fun KtorDefaultHttpClient(json: Json): HttpClient =
    HttpClient {
        setDefaultConfig(json)
    }
