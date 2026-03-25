package org.yarokovisty.delivery.feature.direction.impl.data.repository

import org.yarokovisty.delivery.feature.direction.api.domain.entity.DeliveryPoint
import org.yarokovisty.delivery.feature.direction.api.domain.repository.DirectionRepository
import org.yarokovisty.delivery.feature.direction.impl.data.mapper.toItem
import org.yarokovisty.delivery.feature.direction.impl.data.service.DirectionService

internal class DirectionRepositoryImpl(
    private val service: DirectionService
) : DirectionRepository {

    override suspend fun getDeliveryPoints(): List<DeliveryPoint> =
        service.getDeliveryPoints().toItem()
}
