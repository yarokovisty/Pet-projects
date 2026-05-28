package org.yarokovisty.delivery.common.delivery.order.domain.usecase

import org.yarokovisty.delivery.common.auth.domain.repository.AuthRepository
import org.yarokovisty.delivery.common.delivery.order.domain.entity.Order
import org.yarokovisty.delivery.common.delivery.order.domain.repository.OrderRepository

class GetHistoryOrdersUseCase(
    private val authRepository: AuthRepository,
    private val orderRepository: OrderRepository,
) {

    suspend operator fun invoke(): List<Order> {
        val token = authRepository.getToken() ?: return emptyList()
        return orderRepository.getHistory(token)
    }
}
