package org.yarokovisty.delivery.feature.delivery.main.api.domain.repository

import org.yarokovisty.delivery.feature.delivery.main.api.domain.model.DeliveryPoint
import org.yarokovisty.delivery.feature.delivery.main.api.domain.model.ParcelType

interface DeliveryRepository {

    suspend fun getDeliveryPoints(): List<DeliveryPoint>

    suspend fun getParcelTypes(): List<ParcelType>
}
