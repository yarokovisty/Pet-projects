package org.yarokovisty.delivery.feature.direction.impl.data.service

import io.ktor.client.HttpClient
import org.yarokovisty.delivery.core.network.client.get
import org.yarokovisty.delivery.feature.direction.impl.data.model.DeliveryPointListResponse

internal class DirectionServiceImpl(private val httpClient: HttpClient) : DirectionService {

    override suspend fun getDeliveryPoints(): DeliveryPointListResponse =
        httpClient.get("/api/delivery/points")
}
