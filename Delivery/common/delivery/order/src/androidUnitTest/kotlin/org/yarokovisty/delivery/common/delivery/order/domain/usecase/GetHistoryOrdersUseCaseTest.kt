package org.yarokovisty.delivery.common.delivery.order.domain.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.yarokovisty.delivery.common.auth.domain.repository.AuthRepository
import org.yarokovisty.delivery.common.delivery.order.domain.entity.Order
import org.yarokovisty.delivery.common.delivery.order.domain.repository.OrderRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class GetHistoryOrdersUseCaseTest {

    private val authRepository: AuthRepository = mockk()
    private val orderRepository: OrderRepository = mockk()
    private val useCase = GetHistoryOrdersUseCase(authRepository, orderRepository)

    @Test
    fun `invoke when token is null EXPECT empty list returned`() = runTest {
        coEvery { authRepository.getToken() } returns null

        val result = useCase.invoke()

        assertTrue(result.isEmpty())
    }

    @Test
    fun `invoke when token is null EXPECT order repository not called`() = runTest {
        coEvery { authRepository.getToken() } returns null

        useCase.invoke()

        coVerify(exactly = 0) { orderRepository.getHistory(any()) }
    }

    @Test
    fun `invoke when token is valid EXPECT order repository called with exact token`() = runTest {
        val token = "valid-token-123"
        coEvery { authRepository.getToken() } returns token
        coEvery { orderRepository.getHistory(token) } returns emptyList()

        useCase.invoke()

        coVerify(exactly = 1) { orderRepository.getHistory(token) }
    }

    @Test
    fun `invoke when token is valid and repository returns orders EXPECT orders returned`() = runTest {
        val token = "valid-token-123"
        val expectedOrders = listOf(
            mockk<Order>(relaxed = true),
            mockk<Order>(relaxed = true),
            mockk<Order>(relaxed = true)
        )
        coEvery { authRepository.getToken() } returns token
        coEvery { orderRepository.getHistory(token) } returns expectedOrders

        val result = useCase.invoke()

        assertEquals(expectedOrders, result)
    }

    @Test
    fun `invoke when token is valid and repository returns empty list EXPECT empty list returned`() = runTest {
        val token = "valid-token-123"
        coEvery { authRepository.getToken() } returns token
        coEvery { orderRepository.getHistory(token) } returns emptyList()

        val result = useCase.invoke()

        assertTrue(result.isEmpty())
    }

    @Test
    fun `invoke when token is valid and repository throws exception EXPECT exception propagated`() = runTest {
        val token = "valid-token-123"
        coEvery { authRepository.getToken() } returns token
        coEvery { orderRepository.getHistory(token) } throws RuntimeException("Network error")

        assertFailsWith<RuntimeException> {
            useCase.invoke()
        }
    }
}
