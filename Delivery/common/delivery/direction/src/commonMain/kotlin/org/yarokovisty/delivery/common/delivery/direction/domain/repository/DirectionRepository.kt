package org.yarokovisty.delivery.common.delivery.direction.domain.repository

import org.yarokovisty.delivery.common.delivery.direction.domain.entity.DeliveryPoint

interface DirectionRepository {

    suspend fun getDeliveryPointList(): List<DeliveryPoint>
}
