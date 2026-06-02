package org.yarokovisty.delivery.core.network.config

import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.yarokovisty.delivery.core.network.extenstions.installResponseValidation
import org.yarokovisty.delivery.core.network.util.isDebug
import org.yarokovisty.delivery.util.logger.DeliveryLogger

private const val BASE_URL = "https://juniorsbootcamp.ru"
private const val NETWORK_LOGGING_TAG = "HttpLogging"
private const val CONNECT_TIMEOUT = 15_000L
private const val REQUEST_TIMEOUT = 30_000L

internal fun HttpClientConfig<*>.setDefaultConfig(json: Json) {
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

    installResponseValidation()

    if (isDebug) {
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    DeliveryLogger.i(NETWORK_LOGGING_TAG, message)
                }
            }
            level = LogLevel.ALL
        }
    }
}
