package org.yarokovisty.delivery.feature.direction.impl.data.repository

import org.yarokovisty.delivery.feature.direction.api.domain.entity.DeliveryPoint
import org.yarokovisty.delivery.feature.direction.api.domain.repository.DirectionRepository
import org.yarokovisty.delivery.feature.direction.impl.data.datasource.DirectionRemoteDataSource
import org.yarokovisty.delivery.feature.direction.impl.data.mapper.toItem

internal class DirectionRepositoryImpl(
    private val remoteDataSource: DirectionRemoteDataSource
) : DirectionRepository {

    override suspend fun getDeliveryPoints(): List<DeliveryPoint> =
        remoteDataSource.getDeliveryPoints().toItem()
}
