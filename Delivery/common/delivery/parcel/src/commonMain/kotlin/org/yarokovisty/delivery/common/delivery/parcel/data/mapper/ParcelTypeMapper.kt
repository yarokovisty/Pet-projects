package org.yarokovisty.delivery.common.delivery.parcel.data.mapper

import org.yarokovisty.delivery.common.delivery.parcel.data.model.PackageTypeListResponse
import org.yarokovisty.delivery.common.delivery.parcel.data.model.PackageTypeResponse
import org.yarokovisty.delivery.common.delivery.parcel.domain.entity.ParcelInfo
import org.yarokovisty.delivery.util.kotlin.enums.toEnum

internal fun PackageTypeListResponse.toItem(): List<ParcelInfo> =
    packages.map(PackageTypeResponse::toItem)

fun PackageTypeResponse.toItem(): ParcelInfo =
    ParcelInfo(
        id = this.id,
        type = this.id.toEnum(),
        name = this.name,
        length = this.length,
        width = this.width,
        height = this.height,
        weight = this.weight,
    )
