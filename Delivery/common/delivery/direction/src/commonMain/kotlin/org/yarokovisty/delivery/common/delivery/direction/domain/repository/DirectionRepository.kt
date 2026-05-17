package org.yarokovisty.delivery.common.delivery.direction.domain.repository

import org.yarokovisty.delivery.common.delivery.direction.domain.entity.DeliveryPoint

interface DirectionRepository {

    suspend fun getDeliveryPointList(): List<DeliveryPoint>

    suspend fun getSelectedPointFrom(): DeliveryPoint?

    suspend fun setSelectedPointFrom(point: DeliveryPoint)

    suspend fun clearSelectedPointFrom()

    suspend fun setSelectedPointTo(point: DeliveryPoint)

    suspend fun getSelectedPointTo(): DeliveryPoint?

    suspend fun clearSelectedPointTo()
}
