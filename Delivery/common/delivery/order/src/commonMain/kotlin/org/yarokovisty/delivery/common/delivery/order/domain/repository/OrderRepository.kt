package org.yarokovisty.delivery.common.delivery.order.domain.repository

import org.yarokovisty.delivery.common.delivery.order.domain.entity.ConfirmationOrder
import org.yarokovisty.delivery.common.delivery.order.domain.entity.Order

interface OrderRepository {

    suspend fun get(orderId: String, token: String): Order

    suspend fun getHistory(token: String): List<Order>

    suspend fun create(confirmation: ConfirmationOrder): Order

    suspend fun cancel(orderId: String, token: String)
}
