package org.yarokovisty.delivery.feature.direction.api.domain.repository

import org.yarokovisty.delivery.feature.direction.api.domain.entity.DeliveryPoint

interface DirectionRepository {

    suspend fun getDeliveryPoints(): List<DeliveryPoint>
}
