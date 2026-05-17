package org.yarokovisty.delivery.common.delivery.parcel.data.repository

import org.yarokovisty.delivery.common.delivery.parcel.data.datasource.DeliveryLocalDataSource
import org.yarokovisty.delivery.common.delivery.parcel.data.datasource.DeliveryRemoteDataSource
import org.yarokovisty.delivery.common.delivery.parcel.data.mapper.toItem
import org.yarokovisty.delivery.common.delivery.parcel.domain.entity.ParcelInfo
import org.yarokovisty.delivery.common.delivery.parcel.domain.repository.ParcelRepository

internal class ParcelRepositoryImpl(
    private val localDataSource: DeliveryLocalDataSource,
    private val remoteDataSource: DeliveryRemoteDataSource,
) : ParcelRepository {

    override suspend fun getParcelInfoList(): List<ParcelInfo> =
        remoteDataSource.getPackageTypes().toItem()

    override suspend fun getSelectedParcel(): ParcelInfo? =
        localDataSource.getParcel()

    override suspend fun saveSelectedParcel(parcelInfo: ParcelInfo) {
        localDataSource.saveParcel(parcelInfo)
    }

    override suspend fun clearSelectedParcel() {
        localDataSource.clearParcel()
    }
}
