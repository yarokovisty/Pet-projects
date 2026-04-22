package org.yarokovisty.delivery.common.delivery.parcel.data.datasource

import io.ktor.client.HttpClient
import org.yarokovisty.delivery.common.delivery.parcel.data.model.PackageTypeListResponse
import org.yarokovisty.delivery.core.network.client.get

internal class DeliveryRemoteDataSource(private val httpClient: HttpClient) {

    suspend fun getPackageTypes(): PackageTypeListResponse =
        httpClient.get("/api/delivery/package/types")
}
