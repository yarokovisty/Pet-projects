package org.yarokovisty.delivery.feature.history.main.presentation.viewmodel

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.yarokovisty.delivery.common.delivery.order.domain.entity.Order
import org.yarokovisty.delivery.common.delivery.order.domain.repository.OrderRepository
import org.yarokovisty.delivery.core.common.error.NetworkException
import org.yarokovisty.delivery.feature.history.main.navigation.HistoryMainRouter
import org.yarokovisty.delivery.feature.history.main.presentation.intent.HistoryMainIntent
import org.yarokovisty.delivery.feature.history.main.presentation.state.Error
import org.yarokovisty.delivery.util.unitTest.MainDispatcherRule
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
internal class HistoryMainViewModelTest {

    private val orderRepository: OrderRepository = mockk()
    private val router: HistoryMainRouter = mockk(relaxed = true)

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private fun createViewModel(): HistoryMainViewModel =
        HistoryMainViewModel(
            orderRepository = orderRepository,
            router = router,
        )

    // region Init

    @Test
    fun `init EXPECT loading is false`() {
        val viewModel = createViewModel()

        val actual = viewModel.state.value.loading

        assertFalse(actual)
    }

    @Test
    fun `init EXPECT error is null`() {
        val viewModel = createViewModel()

        val actual = viewModel.state.value.error

        assertNull(actual)
    }

    @Test
    fun `init EXPECT orders is empty list`() {
        val viewModel = createViewModel()

        val actual = viewModel.state.value.orders

        assertEquals(emptyList(), actual)
    }

    // endregion

    // region LoadData - Success

    @Test
    fun `load data EXPECT order repository getHistory called`() = runTest {
        coEvery { orderRepository.getHistory() } returns emptyList()
        val viewModel = createViewModel()

        viewModel.onIntent(HistoryMainIntent.LoadData)
        advanceUntilIdle()

        coVerify { orderRepository.getHistory() }
    }

    @Test
    fun `load data with orders returned EXPECT state orders contains returned orders`() = runTest {
        val expectedOrders = listOf(
            mockk<Order>(relaxed = true),
            mockk<Order>(relaxed = true),
        )
        coEvery { orderRepository.getHistory() } returns expectedOrders
        val viewModel = createViewModel()

        viewModel.onIntent(HistoryMainIntent.LoadData)
        advanceUntilIdle()

        assertEquals(expectedOrders, viewModel.state.value.orders)
    }

    @Test
    fun `load data with orders returned EXPECT state loading is false`() = runTest {
        val orders = listOf(mockk<Order>(relaxed = true))
        coEvery { orderRepository.getHistory() } returns orders
        val viewModel = createViewModel()

        viewModel.onIntent(HistoryMainIntent.LoadData)
        advanceUntilIdle()

        assertFalse(viewModel.state.value.loading)
    }

    @Test
    fun `load data with orders returned EXPECT state error is null`() = runTest {
        val orders = listOf(mockk<Order>(relaxed = true))
        coEvery { orderRepository.getHistory() } returns orders
        val viewModel = createViewModel()

        viewModel.onIntent(HistoryMainIntent.LoadData)
        advanceUntilIdle()

        assertNull(viewModel.state.value.error)
    }

    @Test
    fun `load data with empty list returned EXPECT state orders is empty`() = runTest {
        coEvery { orderRepository.getHistory() } returns emptyList()
        val viewModel = createViewModel()

        viewModel.onIntent(HistoryMainIntent.LoadData)
        advanceUntilIdle()

        assertEquals(emptyList(), viewModel.state.value.orders)
    }

    // endregion

    // region LoadData - Unauthorized Error

    @Test
    fun `load data when repository throws unauthorized EXPECT state error is Unauthorized`() = runTest {
        coEvery { orderRepository.getHistory() } throws NetworkException.Unauthorized
        val viewModel = createViewModel()

        viewModel.onIntent(HistoryMainIntent.LoadData)
        advanceUntilIdle()

        assertEquals(Error.Unauthorized, viewModel.state.value.error)
    }

    @Test
    fun `load data when repository throws unauthorized EXPECT state loading is false`() = runTest {
        coEvery { orderRepository.getHistory() } throws NetworkException.Unauthorized
        val viewModel = createViewModel()

        viewModel.onIntent(HistoryMainIntent.LoadData)
        advanceUntilIdle()

        assertFalse(viewModel.state.value.loading)
    }

    // endregion

    // region LoadData - Unknown Error

    @Test
    fun `load data when repository throws unknown exception EXPECT state error is Unknown`() = runTest {
        coEvery { orderRepository.getHistory() } throws RuntimeException("test error")
        val viewModel = createViewModel()

        viewModel.onIntent(HistoryMainIntent.LoadData)
        advanceUntilIdle()

        assertEquals(Error.Unknown, viewModel.state.value.error)
    }

    @Test
    fun `load data when repository throws unknown exception EXPECT state loading is false`() = runTest {
        coEvery { orderRepository.getHistory() } throws RuntimeException("test error")
        val viewModel = createViewModel()

        viewModel.onIntent(HistoryMainIntent.LoadData)
        advanceUntilIdle()

        assertFalse(viewModel.state.value.loading)
    }

    @Test
    fun `load data when repository throws NetworkException Unknown EXPECT state error is Unknown`() = runTest {
        coEvery { orderRepository.getHistory() } throws NetworkException.Unknown
        val viewModel = createViewModel()

        viewModel.onIntent(HistoryMainIntent.LoadData)
        advanceUntilIdle()

        assertEquals(Error.Unknown, viewModel.state.value.error)
    }

    // endregion

    // region OpenOrderDetails

    @Test
    fun `open order details EXPECT router opens order details screen`() {
        val orderId = "order-123"
        val viewModel = createViewModel()

        viewModel.onIntent(HistoryMainIntent.OpenOrderDetails(orderId))

        verify { router.openOrderDetailsScreen(orderId) }
    }

    // endregion

    // region OpenLoginScreen

    @Test
    fun `open login screen EXPECT router opens login screen`() {
        val viewModel = createViewModel()

        viewModel.onIntent(HistoryMainIntent.OpenLoginScreen)

        verify { router.openLoginScreen() }
    }

    // endregion
}
