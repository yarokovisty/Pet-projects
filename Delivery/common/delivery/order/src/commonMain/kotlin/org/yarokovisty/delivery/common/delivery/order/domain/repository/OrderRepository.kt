package org.yarokovisty.delivery.common.delivery.order.domain.repository

import org.yarokovisty.delivery.common.delivery.order.domain.entity.ConfirmationOrder
import org.yarokovisty.delivery.common.delivery.order.domain.entity.Order

interface OrderRepository {

    suspend fun createOrder(confirmation: ConfirmationOrder): Order
}
