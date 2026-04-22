package org.yarokovisty.delivery.common.delivery.calculator.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CalculatorRequest(
    @SerialName("package")
    val packageRequest: PackageRequest,
    @SerialName("senderPoint")
    val senderPoint: DeliveryPointRequest,
    @SerialName("receiverPoint")
    val receiverPoint: DeliveryPointRequest
)
