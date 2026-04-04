package org.yarokovisty.delivery.feature.direction.impl.data.datasource

import io.ktor.client.HttpClient
import org.yarokovisty.delivery.core.network.client.get
import org.yarokovisty.delivery.feature.direction.impl.data.model.DeliveryPointListResponse

internal class DirectionRemoteDataSource(private val httpClient: HttpClient) {

    suspend fun getDeliveryPoints(): DeliveryPointListResponse =
        httpClient.get("/api/delivery/points")
}
