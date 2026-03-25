package org.yarokovisty.delivery.feature.delivery.main.impl.data.service

import io.ktor.client.HttpClient
import org.yarokovisty.delivery.core.network.client.get
import org.yarokovisty.delivery.feature.delivery.main.impl.data.model.TypePackageListResponse

internal class DeliveryServiceImpl(private val httpClient: HttpClient) : DeliveryService {

    override suspend fun getPackageTypes(): TypePackageListResponse =
        httpClient.get("/api/delivery/package/types")
}
