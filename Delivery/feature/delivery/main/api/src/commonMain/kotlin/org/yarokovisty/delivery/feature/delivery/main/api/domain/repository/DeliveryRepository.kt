package org.yarokovisty.delivery.feature.delivery.main.api.domain.repository

import org.yarokovisty.delivery.feature.delivery.main.api.domain.entity.ParcelInfo

interface DeliveryRepository {

    suspend fun getParcelTypes(): List<ParcelInfo>
}
