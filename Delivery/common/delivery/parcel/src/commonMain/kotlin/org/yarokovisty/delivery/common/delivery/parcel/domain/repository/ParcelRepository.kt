package org.yarokovisty.delivery.common.delivery.parcel.domain.repository

import org.yarokovisty.delivery.common.delivery.parcel.domain.entity.ParcelInfo

interface ParcelRepository {

    suspend fun getParcelInfoList(): List<ParcelInfo>
}
