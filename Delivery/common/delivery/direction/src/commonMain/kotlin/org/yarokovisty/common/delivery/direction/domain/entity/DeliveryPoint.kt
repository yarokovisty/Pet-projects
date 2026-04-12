package org.yarokovisty.common.delivery.direction.domain.entity

data class DeliveryPoint(
    val id: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
)
