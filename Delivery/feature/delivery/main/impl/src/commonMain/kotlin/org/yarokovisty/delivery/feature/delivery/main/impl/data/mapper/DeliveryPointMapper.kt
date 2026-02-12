package org.yarokovisty.delivery.feature.delivery.main.impl.data.mapper

import org.yarokovisty.delivery.feature.delivery.main.api.domain.model.DeliveryPoint
import org.yarokovisty.delivery.feature.delivery.main.impl.data.model.DeliveryPointListResponse
import org.yarokovisty.delivery.feature.delivery.main.impl.data.model.DeliveryPointResponse

internal fun DeliveryPointListResponse.toItem(): List<DeliveryPoint> =
    points.map(DeliveryPointResponse::toItem)

internal fun DeliveryPointResponse.toItem(): DeliveryPoint =
    DeliveryPoint(
        id = this.id,
        name = this.name,
        latitude = this.latitude,
        longitude = this.longitude,
    )
