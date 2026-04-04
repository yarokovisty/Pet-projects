package org.yarokovisty.delivery.feature.delivery.main.impl.data.repository

import org.yarokovisty.delivery.feature.delivery.main.api.domain.entity.ParcelType
import org.yarokovisty.delivery.feature.delivery.main.api.domain.repository.DeliveryRepository
import org.yarokovisty.delivery.feature.delivery.main.impl.data.datasource.DeliveryRemoteDataSource
import org.yarokovisty.delivery.feature.delivery.main.impl.data.mapper.toItem

internal class DeliveryRepositoryImpl(
    private val remoteDataSource: DeliveryRemoteDataSource,
) : DeliveryRepository {

    override suspend fun getParcelTypes(): List<ParcelType> =
        remoteDataSource.getPackageTypes().toItem()
}
