package org.yarokovisty.delivery.common.delivery.direction.data.mapper

import org.yarokovisty.delivery.common.delivery.direction.data.model.DeliveryPointListResponse
import org.yarokovisty.delivery.common.delivery.direction.data.model.DeliveryPointResponse
import org.yarokovisty.delivery.common.delivery.direction.domain.entity.DeliveryPoint

internal fun DeliveryPointListResponse.toItem(): List<DeliveryPoint> =
    points.map(DeliveryPointResponse::toItem)

fun DeliveryPointResponse.toItem(): DeliveryPoint =
    DeliveryPoint(
        id = this.id,
        name = this.name,
        latitude = this.latitude,
        longitude = this.longitude,
    )
