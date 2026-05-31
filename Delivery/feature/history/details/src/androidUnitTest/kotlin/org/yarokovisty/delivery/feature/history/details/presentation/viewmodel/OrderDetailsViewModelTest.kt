package org.yarokovisty.delivery.feature.history.details.presentation.viewmodel

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.yarokovisty.delivery.common.delivery.order.domain.entity.Order
import org.yarokovisty.delivery.common.delivery.order.domain.usecase.CancelOrderUseCase
import org.yarokovisty.delivery.common.delivery.order.domain.usecase.GetOrderUseCase
import org.yarokovisty.delivery.feature.history.details.navigation.OrderDetailsRouter
import org.yarokovisty.delivery.feature.history.details.presentation.intent.OrderDetailsIntent
import org.yarokovisty.delivery.feature.history.details.presentation.state.OrderDetailsError
import org.yarokovisty.delivery.util.unitTest.MainDispatcherRule
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
internal class OrderDetailsViewModelTest {

    private val cancelOrderUseCase: CancelOrderUseCase = mockk(relaxed = true)
    private val getOrderUseCase: GetOrderUseCase = mockk()
    private val router: OrderDetailsRouter = mockk(relaxed = true)

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private companion object {
        const val ORDER_ID = "order-123"
    }

    private fun createViewModel(): OrderDetailsViewModel =
        OrderDetailsViewModel(
            cancelOrderUseCase = cancelOrderUseCase,
            getOrderUseCase = getOrderUseCase,
            router = router,
            orderId = ORDER_ID,
        )

    // region Init - LoadData Success

    @Test
    fun `init with successful load EXPECT loading is false`() = runTest {
        val order = mockk<Order>(relaxed = true)
        coEvery { getOrderUseCase(ORDER_ID) } returns order

        val viewModel = createViewModel()
        advanceUntilIdle()

        assertFalse(viewModel.state.value.loading)
    }

    @Test
    fun `init with successful load EXPECT order is set`() = runTest {
        val order = mockk<Order>(relaxed = true)
        coEvery { getOrderUseCase(ORDER_ID) } returns order

        val viewModel = createViewModel()
        advanceUntilIdle()

        assertEquals(order, viewModel.state.value.order)
    }

    @Test
    fun `init with successful load EXPECT error is null`() = runTest {
        val order = mockk<Order>(relaxed = true)
        coEvery { getOrderUseCase(ORDER_ID) } returns order

        val viewModel = createViewModel()
        advanceUntilIdle()

        assertNull(viewModel.state.value.error)
    }

    // endregion

    // region Init - LoadData Error

    @Test
    fun `init when use case throws EXPECT error is LOAD`() = runTest {
        coEvery { getOrderUseCase(ORDER_ID) } throws RuntimeException("Network error")

        val viewModel = createViewModel()
        advanceUntilIdle()

        assertEquals(OrderDetailsError.LOAD, viewModel.state.value.error)
    }

    @Test
    fun `init when use case throws EXPECT loading is false`() = runTest {
        coEvery { getOrderUseCase(ORDER_ID) } throws RuntimeException("Network error")

        val viewModel = createViewModel()
        advanceUntilIdle()

        assertFalse(viewModel.state.value.loading)
    }

    // endregion

    // region LoadData Intent

    @Test
    fun `load data intent EXPECT get order use case called`() = runTest {
        val order = mockk<Order>(relaxed = true)
        coEvery { getOrderUseCase(ORDER_ID) } returns order

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(OrderDetailsIntent.LoadData)
        advanceUntilIdle()

        coVerify(exactly = 2) { getOrderUseCase(ORDER_ID) }
    }

    // endregion

    // region OpenCancellationScreen

    @Test
    fun `open cancellation screen EXPECT cancellation screen visible is true`() = runTest {
        val order = mockk<Order>(relaxed = true)
        coEvery { getOrderUseCase(ORDER_ID) } returns order

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(OrderDetailsIntent.OpenCancellationScreen)

        assertTrue(viewModel.state.value.cancellationScreenVisible)
    }

    // endregion

    // region CloseCancellationScreen

    @Test
    fun `close cancellation screen EXPECT cancellation screen visible is false`() = runTest {
        val order = mockk<Order>(relaxed = true)
        coEvery { getOrderUseCase(ORDER_ID) } returns order

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(OrderDetailsIntent.OpenCancellationScreen)
        viewModel.onIntent(OrderDetailsIntent.CloseCancellationScreen)

        assertFalse(viewModel.state.value.cancellationScreenVisible)
    }

    // endregion

    // region ConfirmCancellation - Success

    @Test
    fun `confirm cancellation success EXPECT successful screen visible is true`() = runTest {
        val order = mockk<Order>(relaxed = true)
        coEvery { getOrderUseCase(ORDER_ID) } returns order

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(OrderDetailsIntent.ConfirmCancellation)
        advanceUntilIdle()

        assertTrue(viewModel.state.value.successfulScreenVisible)
    }

    @Test
    fun `confirm cancellation success EXPECT loading is false`() = runTest {
        val order = mockk<Order>(relaxed = true)
        coEvery { getOrderUseCase(ORDER_ID) } returns order

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(OrderDetailsIntent.ConfirmCancellation)
        advanceUntilIdle()

        assertFalse(viewModel.state.value.loading)
    }

    @Test
    fun `confirm cancellation success EXPECT cancel use case called`() = runTest {
        val order = mockk<Order>(relaxed = true)
        coEvery { getOrderUseCase(ORDER_ID) } returns order

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(OrderDetailsIntent.ConfirmCancellation)
        advanceUntilIdle()

        coVerify(exactly = 1) { cancelOrderUseCase(ORDER_ID) }
    }

    @Test
    fun `confirm cancellation EXPECT cancellation screen hidden`() = runTest {
        val order = mockk<Order>(relaxed = true)
        coEvery { getOrderUseCase(ORDER_ID) } returns order

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(OrderDetailsIntent.OpenCancellationScreen)
        viewModel.onIntent(OrderDetailsIntent.ConfirmCancellation)
        advanceUntilIdle()

        assertFalse(viewModel.state.value.cancellationScreenVisible)
    }

    // endregion

    // region ConfirmCancellation - Error

    @Test
    fun `confirm cancellation when use case throws EXPECT error is CANCEL`() = runTest {
        val order = mockk<Order>(relaxed = true)
        coEvery { getOrderUseCase(ORDER_ID) } returns order
        coEvery { cancelOrderUseCase(ORDER_ID) } throws RuntimeException("Cancel error")

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(OrderDetailsIntent.ConfirmCancellation)
        advanceUntilIdle()

        assertEquals(OrderDetailsError.CANCEL, viewModel.state.value.error)
    }

    @Test
    fun `confirm cancellation when use case throws EXPECT loading is false`() = runTest {
        val order = mockk<Order>(relaxed = true)
        coEvery { getOrderUseCase(ORDER_ID) } returns order
        coEvery { cancelOrderUseCase(ORDER_ID) } throws RuntimeException("Cancel error")

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(OrderDetailsIntent.ConfirmCancellation)
        advanceUntilIdle()

        assertFalse(viewModel.state.value.loading)
    }

    // endregion

    // region Back

    @Test
    fun `back intent EXPECT router back called`() = runTest {
        val order = mockk<Order>(relaxed = true)
        coEvery { getOrderUseCase(ORDER_ID) } returns order

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(OrderDetailsIntent.Back)

        verify { router.back() }
    }

    // endregion
}
