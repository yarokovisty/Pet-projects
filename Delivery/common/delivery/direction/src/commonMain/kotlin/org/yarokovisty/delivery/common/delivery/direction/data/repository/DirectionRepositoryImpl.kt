package org.yarokovisty.delivery.common.delivery.direction.data.repository

import org.yarokovisty.delivery.common.delivery.direction.data.datasource.DirectionLocalDataSource
import org.yarokovisty.delivery.common.delivery.direction.data.datasource.DirectionRemoteDataSource
import org.yarokovisty.delivery.common.delivery.direction.data.mapper.toItem
import org.yarokovisty.delivery.common.delivery.direction.domain.entity.DeliveryPoint
import org.yarokovisty.delivery.common.delivery.direction.domain.repository.DirectionRepository

internal class DirectionRepositoryImpl(
    private val localDataSource: DirectionLocalDataSource,
    private val remoteDataSource: DirectionRemoteDataSource
) : DirectionRepository {

    override suspend fun getDeliveryPointList(): List<DeliveryPoint> =
        remoteDataSource.getDeliveryPoints().toItem()

    override suspend fun getSelectedPointFrom(): DeliveryPoint? =
        localDataSource.getPointFrom()

    override suspend fun setSelectedPointFrom(point: DeliveryPoint) {
        localDataSource.savePointFrom(point)
    }

    override suspend fun clearSelectedPointFrom() {
        localDataSource.clearPointFrom()
    }

    override suspend fun getSelectedPointTo(): DeliveryPoint? =
        localDataSource.getPointTo()

    override suspend fun setSelectedPointTo(point: DeliveryPoint) {
        localDataSource.savePointTo(point)
    }

    override suspend fun clearSelectedPointTo() {
        localDataSource.clearPointTo()
    }
}
