package org.yarokovisty.delivery.common.delivery.calculator.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Option(
    val id: String,
    val price: Double,
    val days: Int,
    val type: OptionType
)
