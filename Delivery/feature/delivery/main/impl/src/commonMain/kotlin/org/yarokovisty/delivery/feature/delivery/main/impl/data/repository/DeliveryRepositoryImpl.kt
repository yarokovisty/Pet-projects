package org.yarokovisty.delivery.feature.delivery.main.impl.data.repository

import org.yarokovisty.delivery.feature.delivery.main.api.domain.entity.ParcelType
import org.yarokovisty.delivery.feature.delivery.main.api.domain.repository.DeliveryRepository
import org.yarokovisty.delivery.feature.delivery.main.impl.data.mapper.toItem
import org.yarokovisty.delivery.feature.delivery.main.impl.data.service.DeliveryService

internal class DeliveryRepositoryImpl(
    private val service: DeliveryService,
) : DeliveryRepository {

    override suspend fun getParcelTypes(): List<ParcelType> =
        service.getPackageTypes().toItem()
}
