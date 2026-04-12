package org.yarokovisty.common.delivery.parcel.data.mapper

import org.yarokovisty.common.delivery.parcel.data.model.PackageTypeListResponse
import org.yarokovisty.common.delivery.parcel.data.model.PackageTypeResponse
import org.yarokovisty.common.delivery.parcel.domain.entity.PackageType
import org.yarokovisty.common.delivery.parcel.domain.entity.ParcelInfo

private const val ENVELOPE = "envelope"
private const val BOX_S = "box-s"
private const val BOX_M = "box-m"
private const val BOX_L = "box-l"
private const val BOX_XL = "box-xl"
private const val BAG = "bag"
private const val PALLET = "pallet"

internal fun PackageTypeListResponse.toItem(): List<ParcelInfo> =
    packages.map(PackageTypeResponse::toItem)

internal fun PackageTypeResponse.toItem(): ParcelInfo =
    ParcelInfo(
        id = this.id,
        type = this.id.convertToPackageType(),
        name = this.name,
        length = this.length,
        width = this.width,
        height = this.height,
        weight = this.weight,
    )

private fun String.convertToPackageType(): PackageType =
    when (this) {
        ENVELOPE -> PackageType.ENVELOPE
        BOX_S -> PackageType.BOX_S
        BOX_M -> PackageType.BOX_M
        BOX_L -> PackageType.BOX_L
        BOX_XL -> PackageType.BOX_XL
        BAG -> PackageType.BAG
        PALLET -> PackageType.PALLET
        else -> error("Unknown parcel type $this")
    }
