package org.yarokovisty.delivery.di.module

import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val serializationModule = module {
    singleOf(::createJsonSerialization)
}

private fun createJsonSerialization(): Json =
    Json {
        ignoreUnknownKeys = true
        prettyPrint = true
        encodeDefaults = false
        explicitNulls = false
    }
