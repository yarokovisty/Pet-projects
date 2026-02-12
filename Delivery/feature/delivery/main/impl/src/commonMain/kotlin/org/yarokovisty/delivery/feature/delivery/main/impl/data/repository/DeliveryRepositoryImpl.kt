package org.yarokovisty.delivery.feature.delivery.main.impl.data.repository

import org.yarokovisty.delivery.feature.delivery.main.api.domain.model.DeliveryPoint
import org.yarokovisty.delivery.feature.delivery.main.api.domain.model.ParcelType
import org.yarokovisty.delivery.feature.delivery.main.api.domain.repository.DeliveryRepository
import org.yarokovisty.delivery.feature.delivery.main.impl.data.datasource.DeliveryRemoteDataSource
import org.yarokovisty.delivery.feature.delivery.main.impl.data.mapper.toItem

internal class DeliveryRepositoryImpl(
    private val deliveryRemoteDataSource: DeliveryRemoteDataSource,
) : DeliveryRepository {

    override suspend fun getDeliveryPoints(): List<DeliveryPoint> =
        deliveryRemoteDataSource.getDeliveryPoints().toItem()

    override suspend fun getParcelTypes(): List<ParcelType> =
        deliveryRemoteDataSource.getPackageTypes().toItem()
}
