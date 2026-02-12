package org.yarokovisty.delivery.feature.delivery.main.impl.data.service

import org.yarokovisty.delivery.feature.delivery.main.impl.data.model.DeliveryPointListResponse
import org.yarokovisty.delivery.feature.delivery.main.impl.data.model.TypePackageListResponse

internal interface DeliveryService {

    suspend fun getDeliveryPoints(): DeliveryPointListResponse

    suspend fun getPackageTypes(): TypePackageListResponse
}
