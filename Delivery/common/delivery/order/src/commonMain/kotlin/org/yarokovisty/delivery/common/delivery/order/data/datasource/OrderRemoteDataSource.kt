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

internal class OrderRemoteDataSource(
    private val defaultHttpClient: HttpClient,
    private val authHttpClient: HttpClient,
) {

    suspend fun get(id: String): OrderResponse =
        authHttpClient.get("api/delivery/orders/$id")

    suspend fun getHistory(): OrderListResponse =
        authHttpClient.get("api/delivery/orders")

    suspend fun create(request: ConfirmationOrderRequest): OrderResponse =
        defaultHttpClient.post<OrderResponse, ConfirmationOrderRequest>("api/delivery/order", request)

    suspend fun cancel(request: CancellationOrderRequest): CancellationOrderResponse =
        authHttpClient.put<CancellationOrderResponse, CancellationOrderRequest>(
            url = "api/delivery/orders/cancel",
            request = request
        )
}
