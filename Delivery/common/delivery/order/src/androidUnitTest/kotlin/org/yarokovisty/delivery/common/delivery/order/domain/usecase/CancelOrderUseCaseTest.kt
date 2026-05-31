package org.yarokovisty.delivery.common.delivery.order.domain.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.yarokovisty.delivery.common.auth.domain.repository.AuthRepository
import org.yarokovisty.delivery.common.delivery.order.domain.repository.OrderRepository
import kotlin.test.Test
import kotlin.test.assertFailsWith

class CancelOrderUseCaseTest {

    private val authRepository: AuthRepository = mockk()
    private val orderRepository: OrderRepository = mockk(relaxed = true)
    private val useCase = CancelOrderUseCase(authRepository, orderRepository)

    @Test
    fun `invoke when token is null EXPECT exception thrown`() = runTest {
        coEvery { authRepository.getToken() } returns null

        assertFailsWith<IllegalArgumentException> {
            useCase.invoke("order-1")
        }
    }

    @Test
    fun `invoke when token is null EXPECT order repository not called`() = runTest {
        coEvery { authRepository.getToken() } returns null

        runCatching { useCase.invoke("order-1") }

        coVerify(exactly = 0) { orderRepository.cancel(any(), any()) }
    }

    @Test
    fun `invoke when token is valid EXPECT order repository called with correct order id`() = runTest {
        val token = "valid-token-123"
        val orderId = "order-1"
        coEvery { authRepository.getToken() } returns token

        useCase.invoke(orderId)

        coVerify(exactly = 1) { orderRepository.cancel(orderId, token) }
    }

    @Test
    fun `invoke when repository throws exception EXPECT exception propagated`() = runTest {
        val token = "valid-token-123"
        coEvery { authRepository.getToken() } returns token
        coEvery { orderRepository.cancel(any(), any()) } throws RuntimeException("Network error")

        assertFailsWith<RuntimeException> {
            useCase.invoke("order-1")
        }
    }
}
