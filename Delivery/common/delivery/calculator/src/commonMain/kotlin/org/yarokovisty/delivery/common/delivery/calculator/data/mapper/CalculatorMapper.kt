package org.yarokovisty.delivery.common.delivery.calculator.data.mapper

import org.yarokovisty.delivery.common.delivery.calculator.data.model.DeliveryPointRequest
import org.yarokovisty.delivery.common.delivery.calculator.data.model.OptionListResponse
import org.yarokovisty.delivery.common.delivery.calculator.data.model.OptionResponse
import org.yarokovisty.delivery.common.delivery.calculator.data.model.PackageRequest
import org.yarokovisty.delivery.common.delivery.calculator.domain.entity.Option
import org.yarokovisty.delivery.common.delivery.direction.domain.entity.DeliveryPoint
import org.yarokovisty.delivery.common.delivery.parcel.domain.entity.ParcelInfo
import org.yarokovisty.delivery.util.kotlin.enums.toEnum
import org.yarokovisty.delivery.util.kotlin.number.toRubles

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
        price = price.toRubles(),
        days = days,
        type = type.toEnum()
    )
