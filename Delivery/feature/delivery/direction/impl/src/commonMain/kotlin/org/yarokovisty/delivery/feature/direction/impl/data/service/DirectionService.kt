package org.yarokovisty.delivery.feature.direction.impl.data.service

import org.yarokovisty.delivery.feature.direction.impl.data.model.DeliveryPointListResponse

internal interface DirectionService {

    suspend fun getDeliveryPoints(): DeliveryPointListResponse
}
