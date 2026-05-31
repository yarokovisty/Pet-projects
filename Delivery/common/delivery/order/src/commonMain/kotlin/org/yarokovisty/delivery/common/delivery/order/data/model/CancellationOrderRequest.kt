package org.yarokovisty.delivery.common.delivery.order.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CancellationOrderRequest(
    @SerialName("orderId")
    val orderId: String
)
