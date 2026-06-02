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
import org.yarokovisty.delivery.common.delivery.order.domain.repository.OrderRepository
import org.yarokovisty.delivery.core.common.error.NetworkException
import org.yarokovisty.delivery.feature.history.details.navigation.OrderDetailsRouter
import org.yarokovisty.delivery.feature.history.details.presentation.intent.OrderDetailsIntent
import org.yarokovisty.delivery.feature.history.details.presentation.state.Error
import org.yarokovisty.delivery.util.unitTest.MainDispatcherRule
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
internal class OrderDetailsViewModelTest {

    private val orderRepository: OrderRepository = mockk(relaxed = true)
    private val router: OrderDetailsRouter = mockk(relaxed = true)

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private companion object {
        const val ORDER_ID = "order-123"
    }

    private fun createViewModel(): OrderDetailsViewModel =
        OrderDetailsViewModel(
            orderRepository = orderRepository,
            router = router,
            orderId = ORDER_ID,
        )

    // region Init - LoadData Success

    @Test
    fun `init with successful load EXPECT loading is false`() = runTest {
        val order = mockk<Order>(relaxed = true)
        coEvery { orderRepository.get(ORDER_ID) } returns order

        val viewModel = createViewModel()
        advanceUntilIdle()

        assertFalse(viewModel.state.value.loading)
    }

    @Test
    fun `init with successful load EXPECT order is set`() = runTest {
        val order = mockk<Order>(relaxed = true)
        coEvery { orderRepository.get(ORDER_ID) } returns order

        val viewModel = createViewModel()
        advanceUntilIdle()

        assertEquals(order, viewModel.state.value.order)
    }

    @Test
    fun `init with successful load EXPECT error is null`() = runTest {
        val order = mockk<Order>(relaxed = true)
        coEvery { orderRepository.get(ORDER_ID) } returns order

        val viewModel = createViewModel()
        advanceUntilIdle()

        assertNull(viewModel.state.value.error)
    }

    // endregion

    // region Init - LoadData Error

    @Test
    fun `init when repository throws EXPECT error is LOAD`() = runTest {
        coEvery { orderRepository.get(ORDER_ID) } throws RuntimeException("Network error")

        val viewModel = createViewModel()
        advanceUntilIdle()

        assertEquals(Error.Load, viewModel.state.value.error)
    }

    @Test
    fun `init when repository throws EXPECT loading is false`() = runTest {
        coEvery { orderRepository.get(ORDER_ID) } throws RuntimeException("Network error")

        val viewModel = createViewModel()
        advanceUntilIdle()

        assertFalse(viewModel.state.value.loading)
    }

    // endregion

    // region Init - LoadData Unauthorized Error

    @Test
    fun `init when repository throws unauthorized EXPECT error is Unauthorized`() = runTest {
        coEvery { orderRepository.get(ORDER_ID) } throws NetworkException.Unauthorized()

        val viewModel = createViewModel()
        advanceUntilIdle()

        assertEquals(Error.Unauthorized, viewModel.state.value.error)
    }

    @Test
    fun `init when repository throws unauthorized EXPECT loading is false`() = runTest {
        coEvery { orderRepository.get(ORDER_ID) } throws NetworkException.Unauthorized()

        val viewModel = createViewModel()
        advanceUntilIdle()

        assertFalse(viewModel.state.value.loading)
    }

    // endregion

    // region LoadData Intent

    @Test
    fun `load data intent EXPECT order repository get called`() = runTest {
        val order = mockk<Order>(relaxed = true)
        coEvery { orderRepository.get(ORDER_ID) } returns order

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(OrderDetailsIntent.LoadData)
        advanceUntilIdle()

        coVerify(exactly = 2) { orderRepository.get(ORDER_ID) }
    }

    // endregion

    // region OpenCancellationScreen

    @Test
    fun `open cancellation screen EXPECT cancellation screen visible is true`() = runTest {
        val order = mockk<Order>(relaxed = true)
        coEvery { orderRepository.get(ORDER_ID) } returns order

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
        coEvery { orderRepository.get(ORDER_ID) } returns order

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
        coEvery { orderRepository.get(ORDER_ID) } returns order

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(OrderDetailsIntent.ConfirmCancellation)
        advanceUntilIdle()

        assertTrue(viewModel.state.value.successfulScreenVisible)
    }

    @Test
    fun `confirm cancellation success EXPECT loading is false`() = runTest {
        val order = mockk<Order>(relaxed = true)
        coEvery { orderRepository.get(ORDER_ID) } returns order

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(OrderDetailsIntent.ConfirmCancellation)
        advanceUntilIdle()

        assertFalse(viewModel.state.value.loading)
    }

    @Test
    fun `confirm cancellation success EXPECT cancel called on repository`() = runTest {
        val order = mockk<Order>(relaxed = true)
        coEvery { orderRepository.get(ORDER_ID) } returns order

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(OrderDetailsIntent.ConfirmCancellation)
        advanceUntilIdle()

        coVerify(exactly = 1) { orderRepository.cancel(ORDER_ID) }
    }

    @Test
    fun `confirm cancellation EXPECT cancellation screen hidden`() = runTest {
        val order = mockk<Order>(relaxed = true)
        coEvery { orderRepository.get(ORDER_ID) } returns order

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
    fun `confirm cancellation when repository throws EXPECT error is CANCEL`() = runTest {
        val order = mockk<Order>(relaxed = true)
        coEvery { orderRepository.get(ORDER_ID) } returns order
        coEvery { orderRepository.cancel(ORDER_ID) } throws RuntimeException("Cancel error")

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(OrderDetailsIntent.ConfirmCancellation)
        advanceUntilIdle()

        assertEquals(Error.Cancel, viewModel.state.value.error)
    }

    @Test
    fun `confirm cancellation when repository throws EXPECT loading is false`() = runTest {
        val order = mockk<Order>(relaxed = true)
        coEvery { orderRepository.get(ORDER_ID) } returns order
        coEvery { orderRepository.cancel(ORDER_ID) } throws RuntimeException("Cancel error")

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
        coEvery { orderRepository.get(ORDER_ID) } returns order

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(OrderDetailsIntent.Back)

        verify { router.back() }
    }

    // endregion

    // region OpenLoginScreen

    @Test
    fun `open login screen intent EXPECT router open login screen called`() = runTest {
        val order = mockk<Order>(relaxed = true)
        coEvery { orderRepository.get(ORDER_ID) } returns order

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(OrderDetailsIntent.OpenLoginScreen)

        verify { router.openLoginScreen() }
    }

    // endregion
}
