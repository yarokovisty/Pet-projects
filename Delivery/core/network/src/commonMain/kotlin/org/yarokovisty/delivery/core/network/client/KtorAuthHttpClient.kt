package org.yarokovisty.delivery.core.network.client

import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json
import org.yarokovisty.delivery.core.network.config.setDefaultConfig
import org.yarokovisty.delivery.core.network.plugin.AuthPlugin
import org.yarokovisty.delivery.core.network.token.TokenProvider

@Suppress("FunctionName")
internal fun KtorAuthHttpClient(
    json: Json,
    tokenProvider: TokenProvider,
): HttpClient =
    HttpClient {
        install(AuthPlugin) {
            this.tokenProvider = tokenProvider
        }

        setDefaultConfig(json)
    }
