package org.yarokovisty.common.delivery.direction.domain.repository

import org.yarokovisty.common.delivery.direction.domain.entity.DeliveryPoint

interface DirectionRepository {

    suspend fun getDeliveryPointList(): List<DeliveryPoint>
}
