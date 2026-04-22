package org.yarokovisty.delivery.common.delivery.calculator.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class OptionResponse(
    @SerialName("id")
    val id: String,
    @SerialName("days")
    val days: Int,
    @SerialName("price")
    val price: Int,
    @SerialName("name")
    val name: String,
    @SerialName("type")
    val type: String
)
