package org.yarokovisty.delivery.feature.profile.main.impl.data.json

import kotlinx.serialization.json.Json

internal fun createJsonSerialization(): Json =
    Json {
        prettyPrint = true
    }
