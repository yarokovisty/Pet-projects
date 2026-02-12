package org.yarokovisty.delivery.feature.delivery.main.api.domain.model

data class DeliveryPoint(
    val id: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
)
