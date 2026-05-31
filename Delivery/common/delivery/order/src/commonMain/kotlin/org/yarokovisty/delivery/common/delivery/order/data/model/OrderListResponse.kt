package org.yarokovisty.delivery.common.delivery.order.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class OrderListResponse(
    @SerialName("orders")
    val orders: List<OrderDto>,
)
