package org.yarokovisty.delivery.common.delivery.order.domain.usecase

import org.yarokovisty.delivery.common.auth.domain.repository.AuthRepository
import org.yarokovisty.delivery.common.delivery.order.domain.entity.Order
import org.yarokovisty.delivery.common.delivery.order.domain.repository.OrderRepository

class GetOrderUseCase(
    private val authRepository: AuthRepository,
    private val orderRepository: OrderRepository,
) {

    suspend operator fun invoke(orderId: String): Order {
        val token = requireNotNull(authRepository.getToken())
        return orderRepository.get(orderId, token)
    }
}
