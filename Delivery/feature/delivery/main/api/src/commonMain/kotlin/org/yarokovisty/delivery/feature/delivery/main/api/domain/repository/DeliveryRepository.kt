package org.yarokovisty.delivery.feature.delivery.main.api.domain.repository

import org.yarokovisty.delivery.feature.delivery.main.api.domain.entity.ParcelType

interface DeliveryRepository {

    suspend fun getParcelTypes(): List<ParcelType>
}
