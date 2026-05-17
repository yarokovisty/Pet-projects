package org.yarokovisty.delivery.common.delivery.parcel.data.datasource

import org.yarokovisty.delivery.common.delivery.parcel.domain.entity.ParcelInfo
import org.yarokovisty.delivery.core.storage.preferences.PreferencesStorage

internal class DeliveryLocalDataSource(private val storage: PreferencesStorage) {

    private companion object {
        const val PARCEL_INFO_KEY = "selected_parcel_info"
    }

    suspend fun getParcel(): ParcelInfo? =
        storage.getObject(PARCEL_INFO_KEY, ParcelInfo.serializer())

    suspend fun saveParcel(parcelInfo: ParcelInfo) {
        storage.putObject(PARCEL_INFO_KEY, parcelInfo, ParcelInfo.serializer())
    }

    suspend fun clearParcel() {
        storage.remove(PARCEL_INFO_KEY)
    }
}
