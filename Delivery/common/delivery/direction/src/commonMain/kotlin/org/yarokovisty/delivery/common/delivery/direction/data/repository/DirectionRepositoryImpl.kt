package org.yarokovisty.delivery.common.delivery.direction.data.repository

import org.yarokovisty.delivery.common.delivery.direction.data.datasource.DirectionRemoteDataSource
import org.yarokovisty.delivery.common.delivery.direction.data.mapper.toItem
import org.yarokovisty.delivery.common.delivery.direction.domain.entity.DeliveryPoint
import org.yarokovisty.delivery.common.delivery.direction.domain.repository.DirectionRepository

internal class DirectionRepositoryImpl(
    private val remoteDataSource: DirectionRemoteDataSource
) : DirectionRepository {

    override suspend fun getDeliveryPointList(): List<DeliveryPoint> =
        remoteDataSource.getDeliveryPoints().toItem()
}
