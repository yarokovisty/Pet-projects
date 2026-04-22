package org.yarokovisty.delivery.common.delivery.calculator.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PackageRequest(
    @SerialName("length")
    val length: Int,
    @SerialName("width")
    val width: Int,
    @SerialName("weight")
    val weight: Int,
    @SerialName("height")
    val height: Int
)
