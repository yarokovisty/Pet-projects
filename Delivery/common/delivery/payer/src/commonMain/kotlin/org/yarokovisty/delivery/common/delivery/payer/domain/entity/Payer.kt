package org.yarokovisty.delivery.common.delivery.payer.domain.entity

import kotlinx.serialization.Serializable

@Serializable
enum class Payer {
    RECEIVER,
    SENDER
}
