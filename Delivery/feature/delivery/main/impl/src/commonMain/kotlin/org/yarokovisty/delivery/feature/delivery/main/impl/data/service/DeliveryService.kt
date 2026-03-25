package org.yarokovisty.delivery.feature.delivery.main.impl.data.service

import org.yarokovisty.delivery.feature.delivery.main.impl.data.model.TypePackageListResponse

internal interface DeliveryService {

    suspend fun getPackageTypes(): TypePackageListResponse
}
