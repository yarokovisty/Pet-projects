package org.yarokovisty.delivery.feature.history.main.presentation.viewmodel

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.yarokovisty.delivery.common.delivery.order.domain.entity.Order
import org.yarokovisty.delivery.common.delivery.order.domain.usecase.GetHistoryOrdersUseCase
import org.yarokovisty.delivery.feature.history.main.presentation.intent.HistoryMainIntent
import org.yarokovisty.delivery.util.unitTest.MainDispatcherRule
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
internal class HistoryMainViewModelTest {

    private val getHistoryOrdersUseCase: GetHistoryOrdersUseCase = mockk()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private fun createViewModel(): HistoryMainViewModel =
        HistoryMainViewModel(
            getHistoryOrdersUseCase = getHistoryOrdersUseCase,
        )

    // region Init

    @Test
    fun `init EXPECT loading is false`() {
        val viewModel = createViewModel()

        val actual = viewModel.state.value.loading

        assertFalse(actual)
    }

    @Test
    fun `init EXPECT error is false`() {
        val viewModel = createViewModel()

        val actual = viewModel.state.value.error

        assertFalse(actual)
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
    fun `load data EXPECT get history orders use case called`() = runTest {
        coEvery { getHistoryOrdersUseCase() } returns emptyList()
        val viewModel = createViewModel()

        viewModel.onIntent(HistoryMainIntent.LoadData)
        advanceUntilIdle()

        coVerify { getHistoryOrdersUseCase() }
    }

    @Test
    fun `load data with orders returned EXPECT state orders contains returned orders`() = runTest {
        val expectedOrders = listOf(
            mockk<Order>(relaxed = true),
            mockk<Order>(relaxed = true),
        )
        coEvery { getHistoryOrdersUseCase() } returns expectedOrders
        val viewModel = createViewModel()

        viewModel.onIntent(HistoryMainIntent.LoadData)
        advanceUntilIdle()

        assertEquals(expectedOrders, viewModel.state.value.orders)
    }

    @Test
    fun `load data with orders returned EXPECT state loading is false`() = runTest {
        val orders = listOf(mockk<Order>(relaxed = true))
        coEvery { getHistoryOrdersUseCase() } returns orders
        val viewModel = createViewModel()

        viewModel.onIntent(HistoryMainIntent.LoadData)
        advanceUntilIdle()

        assertFalse(viewModel.state.value.loading)
    }

    @Test
    fun `load data with orders returned EXPECT state error is false`() = runTest {
        val orders = listOf(mockk<Order>(relaxed = true))
        coEvery { getHistoryOrdersUseCase() } returns orders
        val viewModel = createViewModel()

        viewModel.onIntent(HistoryMainIntent.LoadData)
        advanceUntilIdle()

        assertFalse(viewModel.state.value.error)
    }

    @Test
    fun `load data with empty list returned EXPECT state orders is empty`() = runTest {
        coEvery { getHistoryOrdersUseCase() } returns emptyList()
        val viewModel = createViewModel()

        viewModel.onIntent(HistoryMainIntent.LoadData)
        advanceUntilIdle()

        assertEquals(emptyList(), viewModel.state.value.orders)
    }

    // endregion

    // region LoadData - Error

    @Test
    fun `load data when use case throws EXPECT state error is true`() = runTest {
        coEvery { getHistoryOrdersUseCase() } throws RuntimeException("test error")
        val viewModel = createViewModel()

        viewModel.onIntent(HistoryMainIntent.LoadData)
        advanceUntilIdle()

        assertTrue(viewModel.state.value.error)
    }

    @Test
    fun `load data when use case throws EXPECT state loading is false`() = runTest {
        coEvery { getHistoryOrdersUseCase() } throws RuntimeException("test error")
        val viewModel = createViewModel()

        viewModel.onIntent(HistoryMainIntent.LoadData)
        advanceUntilIdle()

        assertFalse(viewModel.state.value.loading)
    }

    // endregion
}
