package org.yarokovisty.delivery.feature.direction.impl.data.mapper

import org.yarokovisty.delivery.feature.direction.api.domain.entity.DeliveryPoint
import org.yarokovisty.delivery.feature.direction.impl.data.model.DeliveryPointListResponse
import org.yarokovisty.delivery.feature.direction.impl.data.model.DeliveryPointResponse

internal fun DeliveryPointListResponse.toItem(): List<DeliveryPoint> =
    points.map(DeliveryPointResponse::toItem)

internal fun DeliveryPointResponse.toItem(): DeliveryPoint =
    DeliveryPoint(
        id = this.id,
        name = this.name,
        latitude = this.latitude,
        longitude = this.longitude,
    )
