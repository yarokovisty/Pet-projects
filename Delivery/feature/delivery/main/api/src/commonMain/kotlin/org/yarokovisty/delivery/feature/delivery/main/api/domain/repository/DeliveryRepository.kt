package org.yarokovisty.delivery.feature.delivery.main.api.domain.repository

import org.yarokovisty.delivery.feature.delivery.main.api.domain.entity.DeliveryPoint
import org.yarokovisty.delivery.feature.delivery.main.api.domain.entity.ParcelType

interface DeliveryRepository {

    suspend fun getDeliveryPoints(): List<DeliveryPoint>

    suspend fun getParcelTypes(): List<ParcelType>
}
