package org.pet.project.rickandmorty.core.networking

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.Logger
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.pet.project.rickandmorty.common.utils.PlatformLogger

val client: HttpClient by lazy {
    HttpClient {
        expectSuccess = false

        defaultRequest {
            url(BASE_URL)
        }

        install(ContentNegotiation) {
            json(json)
        }

        install(HttpTimeout) {
            requestTimeoutMillis = NETWORK_TIME_OUT
            connectTimeoutMillis = NETWORK_TIME_OUT
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    PlatformLogger.i(NETWORK_LOGGING, message)
                }
            }
            level = LogLevel.ALL
        }
    }
}

val json: Json by lazy {
    Json {
        ignoreUnknownKeys = true
    }
}

private const val BASE_URL = "https://rickandmortyapi.com"
private const val NETWORK_TIME_OUT = 5_000L
private const val NETWORK_LOGGING = "HttpLogging"
