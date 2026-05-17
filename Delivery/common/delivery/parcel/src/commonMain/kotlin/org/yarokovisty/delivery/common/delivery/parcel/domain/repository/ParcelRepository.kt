package org.yarokovisty.delivery.common.delivery.parcel.domain.repository

import org.yarokovisty.delivery.common.delivery.parcel.domain.entity.ParcelInfo

interface ParcelRepository {

    suspend fun getParcelInfoList(): List<ParcelInfo>

    suspend fun getSelectedParcel(): ParcelInfo?

    suspend fun saveSelectedParcel(parcelInfo: ParcelInfo)

    suspend fun clearSelectedParcel()
}
