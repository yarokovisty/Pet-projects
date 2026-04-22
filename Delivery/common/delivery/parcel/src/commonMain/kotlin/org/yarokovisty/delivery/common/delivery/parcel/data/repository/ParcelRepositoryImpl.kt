package org.yarokovisty.delivery.common.delivery.parcel.data.repository

import org.yarokovisty.delivery.common.delivery.parcel.data.datasource.DeliveryRemoteDataSource
import org.yarokovisty.delivery.common.delivery.parcel.data.mapper.toItem
import org.yarokovisty.delivery.common.delivery.parcel.domain.entity.ParcelInfo
import org.yarokovisty.delivery.common.delivery.parcel.domain.repository.ParcelRepository

internal class ParcelRepositoryImpl(
    private val remoteDataSource: DeliveryRemoteDataSource,
) : ParcelRepository {

    override suspend fun getParcelInfoList(): List<ParcelInfo> =
        remoteDataSource.getPackageTypes().toItem()
}
