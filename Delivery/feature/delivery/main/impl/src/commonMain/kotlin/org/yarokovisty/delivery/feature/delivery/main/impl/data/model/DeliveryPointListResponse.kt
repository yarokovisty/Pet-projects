package org.yarokovisty.delivery.feature.delivery.main.impl.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class DeliveryPointListResponse(
    @SerialName("points")
    val points: List<DeliveryPointResponse>
)
