package org.yarokovisty.common.delivery.calculator.domain.entity

data class Option(
    val id: String,
    val price: Int,
    val days: Int,
    val type: OptionType
)
