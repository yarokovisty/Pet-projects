package org.yarokovisty.common.delivery.parcel.domain.repository

import org.yarokovisty.common.delivery.parcel.domain.entity.ParcelInfo

interface ParcelRepository {

    suspend fun getParcelInfoList(): List<ParcelInfo>
}