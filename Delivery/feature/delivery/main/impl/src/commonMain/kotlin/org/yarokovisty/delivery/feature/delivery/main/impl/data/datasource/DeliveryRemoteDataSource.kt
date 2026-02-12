package org.yarokovisty.delivery.feature.delivery.main.impl.data.datasource

import org.yarokovisty.delivery.core.network.client.HttpService
import org.yarokovisty.delivery.feature.delivery.main.impl.data.model.DeliveryPointListResponse
import org.yarokovisty.delivery.feature.delivery.main.impl.data.model.TypePackageListResponse

internal class DeliveryRemoteDataSource(private val service: HttpService) {

    suspend fun getDeliveryPoints(): DeliveryPointListResponse =
        service.get("/api/delivery/points")

    suspend fun getPackageTypes(): TypePackageListResponse =
        service.get("/api/delivery/package/types")
}
