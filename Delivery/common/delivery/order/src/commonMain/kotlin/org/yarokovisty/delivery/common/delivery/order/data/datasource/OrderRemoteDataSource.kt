package org.yarokovisty.delivery.common.delivery.order.data.datasource

import io.ktor.client.HttpClient
import org.yarokovisty.delivery.common.delivery.order.data.model.CancellationOrderRequest
import org.yarokovisty.delivery.common.delivery.order.data.model.CancellationOrderResponse
import org.yarokovisty.delivery.common.delivery.order.data.model.ConfirmationOrderRequest
import org.yarokovisty.delivery.common.delivery.order.data.model.OrderListResponse
import org.yarokovisty.delivery.common.delivery.order.data.model.OrderResponse
import org.yarokovisty.delivery.core.network.client.get
import org.yarokovisty.delivery.core.network.client.post
import org.yarokovisty.delivery.core.network.client.put

internal class OrderRemoteDataSource(private val httpClient: HttpClient) {

    suspend fun get(id: String, token: String): OrderResponse =
        httpClient.get("api/delivery/orders/$id", token)

    suspend fun getHistory(token: String): OrderListResponse =
        httpClient.get("api/delivery/orders", token)

    suspend fun create(request: ConfirmationOrderRequest): OrderResponse =
        httpClient.post<OrderResponse, ConfirmationOrderRequest>("api/delivery/order", request)

    suspend fun cancel(
        request: CancellationOrderRequest,
        token: String
    ): CancellationOrderResponse =
        httpClient.put<CancellationOrderResponse, CancellationOrderRequest>(
            url = "api/delivery/orders/cancel",
            request = request,
            token = token
        )
}
