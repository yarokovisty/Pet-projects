package org.yarokovisty.shift_pizza.component.network.di

import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.yarokovisty.shift_pizza.component.network.di.Type.DEFAULT
import org.yarokovisty.shift_pizza.component.network.json.createJson
import org.yarokovisty.shift_pizza.component.network.ktor.DefaultKtorHttpClient

@Module
class NetworkModule {

    @Single
    fun provideJson(): Json = createJson()

    @Single
    @NetworkClient(DEFAULT)
    fun provideHttpClient(json: Json): HttpClient = DefaultKtorHttpClient(json)
}
