package org.yarokovisty.delivery.feature.profile.main.data.json

import kotlinx.serialization.json.Json

internal fun createJsonSerialization(): Json =
    Json {
        prettyPrint = true
    }
