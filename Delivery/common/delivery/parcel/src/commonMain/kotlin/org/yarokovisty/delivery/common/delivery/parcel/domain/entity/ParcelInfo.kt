package org.yarokovisty.delivery.common.delivery.parcel.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class ParcelInfo(
    val id: String,
    val type: PackageType,
    val name: String,
    val length: Int,
    val width: Int,
    val height: Int,
    val weight: Int,
)
