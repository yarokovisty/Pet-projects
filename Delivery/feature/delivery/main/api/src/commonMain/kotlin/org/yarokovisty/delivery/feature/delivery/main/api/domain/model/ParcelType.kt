package org.yarokovisty.delivery.feature.delivery.main.api.domain.model

data class ParcelType(
    val id: String,
    val type: PackageType,
    val name: String,
    val length: Int,
    val width: Int,
    val height: Int,
    val weight: Int,
)
