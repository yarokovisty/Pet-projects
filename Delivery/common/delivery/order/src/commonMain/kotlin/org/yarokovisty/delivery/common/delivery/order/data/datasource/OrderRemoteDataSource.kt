package org.yarokovisty.delivery.common.delivery.order.data.datasource

import io.ktor.client.HttpClient
import org.yarokovisty.delivery.common.delivery.order.data.model.ConfirmationOrderRequest
import org.yarokovisty.delivery.common.delivery.order.data.model.CreateOrderResponse
import org.yarokovisty.delivery.core.network.client.post

internal class OrderRemoteDataSource(private val httpClient: HttpClient) {

    suspend fun createOrder(request: ConfirmationOrderRequest): CreateOrderResponse =
        httpClient.post<CreateOrderResponse, ConfirmationOrderRequest>("/api/delivery/order", request)
}
