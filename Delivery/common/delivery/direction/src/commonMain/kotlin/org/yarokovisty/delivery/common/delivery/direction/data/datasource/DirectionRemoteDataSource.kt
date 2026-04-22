package org.yarokovisty.delivery.common.delivery.direction.data.datasource

import io.ktor.client.HttpClient
import org.yarokovisty.delivery.common.delivery.direction.data.model.DeliveryPointListResponse
import org.yarokovisty.delivery.core.network.client.get

internal class DirectionRemoteDataSource(private val httpClient: HttpClient) {

    suspend fun getDeliveryPoints(): DeliveryPointListResponse =
        httpClient.get("/api/delivery/points")
}
