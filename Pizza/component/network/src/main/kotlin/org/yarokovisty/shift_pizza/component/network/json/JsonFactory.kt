package org.yarokovisty.shift_pizza.component.network.json

import kotlinx.serialization.json.Json

internal fun createJson(): Json =
    Json {
        ignoreUnknownKeys = true
        prettyPrint = true
        encodeDefaults = false
        explicitNulls = false
    }
