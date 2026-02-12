package org.yarokovisty.delivery.feature.delivery.main.api.domain.entity

data class DeliveryPoint(
    val id: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
)
