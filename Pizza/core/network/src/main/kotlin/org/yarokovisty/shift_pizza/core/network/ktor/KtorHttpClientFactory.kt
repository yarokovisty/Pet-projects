package org.yarokovisty.shift_pizza.core.network.ktor

import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json

@Suppress("FunctionName")
internal fun DefaultKtorHttpClient(json: Json): HttpClient =
    HttpClient {
        setConfig(json)
    }
