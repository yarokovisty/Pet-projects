package org.yarokovisty.delivery.feature.delivery.order.presentation.viewmodel

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.yarokovisty.delivery.common.delivery.order.domain.repository.OrderRepository
import org.yarokovisty.delivery.common.delivery.order.domain.usecase.ClearConfirmationOrderUseCase
import org.yarokovisty.delivery.common.delivery.order.domain.usecase.GetConfirmationOrderUseCase
import org.yarokovisty.delivery.feature.delivery.order.navigation.ConfirmationOrderRouter
import org.yarokovisty.delivery.feature.delivery.order.presentation.intent.ConfirmationOrderIntent
import org.yarokovisty.delivery.util.phone.PhoneNumberFormatter
import org.yarokovisty.delivery.util.unitTest.MainDispatcherRule
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
internal class ConfirmationOrderViewModelTest {

    private companion object {

        const val MAX_STEPS = 7
        const val CURRENT_STEP = 7
    }

    private val orderRepository: OrderRepository = mockk(relaxed = true)
    private val getConfirmationOrderUseCase: GetConfirmationOrderUseCase = mockk()
    private val clearConfirmationOrderUseCase: ClearConfirmationOrderUseCase = mockk(relaxed = true)
    private val phoneNumberFormatter: PhoneNumberFormatter = mockk(relaxed = true)
    private val router: ConfirmationOrderRouter = mockk(relaxed = true)

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private fun createViewModel(): ConfirmationOrderViewModel =
        ConfirmationOrderViewModel(
            orderRepository = orderRepository,
            getConfirmationOrderUseCase = getConfirmationOrderUseCase,
            clearConfirmationOrderUseCase = clearConfirmationOrderUseCase,
            phoneNumberFormatter = phoneNumberFormatter,
            router = router,
            maxSteps = MAX_STEPS
        )

    // region Init

    @Test
    fun `init EXPECT current step is 7`() {
        val viewModel = createViewModel()

        val actual = viewModel.state.value.stepState.progress

        assertEquals(CURRENT_STEP, actual)
    }

    @Test
    fun `init EXPECT max steps matches constructor parameter`() {
        val viewModel = createViewModel()

        val actual = viewModel.state.value.stepState.maxProgress

        assertEquals(MAX_STEPS, actual)
    }

    @Test
    fun `init EXPECT content is null`() {
        val viewModel = createViewModel()

        val actual = viewModel.state.value.content

        assertNull(actual)
    }

    // endregion

    // region Back

    @Test
    fun `back EXPECT clear confirmation order use case called`() = runTest {
        val viewModel = createViewModel()

        viewModel.onIntent(ConfirmationOrderIntent.Back)
        advanceUntilIdle()

        coVerify { clearConfirmationOrderUseCase() }
    }

    @Test
    fun `back EXPECT router back called`() = runTest {
        val viewModel = createViewModel()

        viewModel.onIntent(ConfirmationOrderIntent.Back)
        advanceUntilIdle()

        verify { router.back() }
    }

    // endregion

    // region Edit Navigation

    @Test
    fun `edit receiver EXPECT router opens receiver screen`() = runTest {
        val viewModel = createViewModel()

        viewModel.onIntent(ConfirmationOrderIntent.EditReceiver)
        advanceUntilIdle()

        verify { router.openReceiverScreen() }
    }

    @Test
    fun `edit sender EXPECT router opens sender screen`() = runTest {
        val viewModel = createViewModel()

        viewModel.onIntent(ConfirmationOrderIntent.EditSender)
        advanceUntilIdle()

        verify { router.openSenderScreen() }
    }

    @Test
    fun `edit receiver address EXPECT router opens receiver address screen`() = runTest {
        val viewModel = createViewModel()

        viewModel.onIntent(ConfirmationOrderIntent.EditReceiverAddress)
        advanceUntilIdle()

        verify { router.openReceiverAddressScreen() }
    }

    @Test
    fun `edit sender address EXPECT router opens sender address screen`() = runTest {
        val viewModel = createViewModel()

        viewModel.onIntent(ConfirmationOrderIntent.EditSenderAddress)
        advanceUntilIdle()

        verify { router.openSenderAddressScreen() }
    }

    // endregion

    // region LoadData

    @Test
    fun `load data EXPECT get confirmation order use case called`() = runTest {
        coEvery { getConfirmationOrderUseCase() } throws RuntimeException("test")
        val viewModel = createViewModel()

        viewModel.onIntent(ConfirmationOrderIntent.LoadData)
        advanceUntilIdle()

        coVerify { getConfirmationOrderUseCase() }
    }

    // endregion

    // region CheckoutOrder

    @Test
    fun `checkout order when content is null EXPECT order repository not called`() = runTest {
        val viewModel = createViewModel()

        viewModel.onIntent(ConfirmationOrderIntent.CheckoutOrder)
        advanceUntilIdle()

        coVerify(exactly = 0) { orderRepository.createOrder(any()) }
    }

    @Test
    fun `checkout order when content is null EXPECT clear confirmation order use case not called`() = runTest {
        val viewModel = createViewModel()

        viewModel.onIntent(ConfirmationOrderIntent.CheckoutOrder)
        advanceUntilIdle()

        coVerify(exactly = 0) { clearConfirmationOrderUseCase() }
    }

    @Test
    fun `checkout order when content is null EXPECT router does not open success order screen`() = runTest {
        val viewModel = createViewModel()

        viewModel.onIntent(ConfirmationOrderIntent.CheckoutOrder)
        advanceUntilIdle()

        verify(exactly = 0) { router.openSuccessOrderScreen() }
    }

    // endregion
}
