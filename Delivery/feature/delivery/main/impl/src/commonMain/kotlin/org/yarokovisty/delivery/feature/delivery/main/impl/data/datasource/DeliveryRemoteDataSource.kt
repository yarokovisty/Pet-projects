package org.yarokovisty.delivery.feature.delivery.main.impl.data.datasource

import io.ktor.client.HttpClient
import org.yarokovisty.delivery.core.network.client.get
import org.yarokovisty.delivery.feature.delivery.main.impl.data.model.TypePackageListResponse

internal class DeliveryRemoteDataSource(private val httpClient: HttpClient) {

    suspend fun getPackageTypes(): TypePackageListResponse =
        httpClient.get("/api/delivery/package/types")
}
