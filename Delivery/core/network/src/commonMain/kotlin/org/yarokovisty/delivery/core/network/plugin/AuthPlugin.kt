package org.yarokovisty.delivery.core.network.plugin

import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.http.HttpHeaders
import org.yarokovisty.delivery.core.network.token.TokenProvider

private const val CONFIG_NAME = "AuthPlugin"

internal class AuthConfig {
    lateinit var tokenProvider: TokenProvider
}

internal val AuthPlugin = createClientPlugin(
    name = CONFIG_NAME,
    createConfiguration = ::AuthConfig
) {
    val tokenProvider = pluginConfig.tokenProvider

    onRequest { request, _ ->
        tokenProvider.get()?.let { token ->
            request.headers.append(HttpHeaders.Authorization, token.addBearer())
        }
    }
}

private fun String.addBearer() = "Bearer $this"
