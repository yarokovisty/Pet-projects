package org.yarokovisty.delivery.common.delivery.order.data.datasource

import io.ktor.client.HttpClient
import org.yarokovisty.delivery.common.delivery.order.data.model.ConfirmationOrderRequest
import org.yarokovisty.delivery.common.delivery.order.data.model.CreateOrderResponse
import org.yarokovisty.delivery.common.delivery.order.data.model.OrderListResponse
import org.yarokovisty.delivery.core.network.client.get
import org.yarokovisty.delivery.core.network.client.post

internal class OrderRemoteDataSource(private val httpClient: HttpClient) {

    suspend fun create(request: ConfirmationOrderRequest): CreateOrderResponse =
        httpClient.post<CreateOrderResponse, ConfirmationOrderRequest>("/api/delivery/order", request)

    suspend fun getHistory(token: String): OrderListResponse =
        httpClient.get("api/delivery/orders", token)
}
