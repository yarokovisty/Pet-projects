package org.yarokovisty.delivery.common.delivery.order.domain.usecase

import org.yarokovisty.delivery.common.auth.domain.repository.AuthRepository
import org.yarokovisty.delivery.common.delivery.order.domain.repository.OrderRepository

class CancelOrderUseCase(
    private val authRepository: AuthRepository,
    private val orderRepository: OrderRepository,
) {

    suspend operator fun invoke(orderId: String) {
        val token = requireNotNull(authRepository.getToken())
        orderRepository.cancel(orderId, token)
    }
}
