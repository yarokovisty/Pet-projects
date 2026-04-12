package org.yarokovisty.common.delivery.direction.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class DeliveryPointListResponse(
    @SerialName("points")
    val points: List<DeliveryPointResponse>
)
