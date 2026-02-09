package org.yarokovisty.delivery.core.network.client

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.yarokovisty.delivery.core.network.util.isDebug
import org.yarokovisty.delivery.util.logger.DeliveryLogger

private const val BASE_URL = "https://juniorsbootcamp.ru"
private const val NETWORK_LOGGING_TAG = "HttpLogging"
private const val CONNECT_TIMEOUT = 15_000L
private const val REQUEST_TIMEOUT = 30_000L

@Suppress("FunctionName")
internal fun KtorHttpClient(): HttpClient =
    HttpClient {
        defaultRequest {
            url(BASE_URL)
        }

        install(HttpTimeout) {
            requestTimeoutMillis = REQUEST_TIMEOUT
            connectTimeoutMillis = CONNECT_TIMEOUT
        }

        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }

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
