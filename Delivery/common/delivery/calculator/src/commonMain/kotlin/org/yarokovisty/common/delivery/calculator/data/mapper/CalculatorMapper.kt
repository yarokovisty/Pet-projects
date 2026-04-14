package org.yarokovisty.common.delivery.calculator.data.mapper

import org.yarokovisty.common.delivery.calculator.data.model.DeliveryPointRequest
import org.yarokovisty.common.delivery.calculator.data.model.OptionListResponse
import org.yarokovisty.common.delivery.calculator.data.model.OptionResponse
import org.yarokovisty.common.delivery.calculator.data.model.PackageRequest
import org.yarokovisty.common.delivery.calculator.domain.entity.Option
import org.yarokovisty.common.delivery.direction.domain.entity.DeliveryPoint
import org.yarokovisty.common.delivery.parcel.domain.entity.ParcelInfo
import org.yarokovisty.delivery.util.kotlin.enums.toEnum

internal fun ParcelInfo.toRequest(): PackageRequest =
    PackageRequest(
        length = length,
        width = width,
        weight = weight,
        height = height
    )

internal fun DeliveryPoint.toRequest(): DeliveryPointRequest =
    DeliveryPointRequest(
        latitude = latitude,
        longitude = longitude
    )

internal fun OptionListResponse.toItem(): List<Option> =
    options.map { it.toItem() }

internal fun OptionResponse.toItem(): Option =
    Option(
        id = id,
        price = price,
        days = days,
        type = type.toEnum()
    )
