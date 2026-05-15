package org.yarokovisty.delivery.common.delivery.calculator.domain.entity

data class Option(
    val id: String,
    val price: Double,
    val days: Int,
    val type: OptionType
)
