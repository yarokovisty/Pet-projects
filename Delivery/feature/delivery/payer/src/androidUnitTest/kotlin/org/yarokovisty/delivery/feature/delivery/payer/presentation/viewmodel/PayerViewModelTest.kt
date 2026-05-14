package org.yarokovisty.delivery.feature.delivery.payer.presentation.viewmodel

import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.yarokovisty.delivery.common.delivery.payer.domain.entity.Payer
import org.yarokovisty.delivery.common.delivery.payer.domain.repository.PayerRepository
import org.yarokovisty.delivery.feature.delivery.payer.navigation.PayerRouter
import org.yarokovisty.delivery.feature.delivery.payer.presentation.intent.PayerIntent
import org.yarokovisty.delivery.util.unitTest.MainDispatcherRule
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
internal class PayerViewModelTest {

    private companion object {

        const val MAX_STEPS = 7
        const val CURRENT_STEP = 6
    }

    private val payerRepository: PayerRepository = mockk(relaxed = true)
    private val router: PayerRouter = mockk(relaxed = true)

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private fun createViewModel(): PayerViewModel =
        PayerViewModel(
            payerRepository = payerRepository,
            router = router,
            maxSteps = MAX_STEPS,
        )

    // region Init

    @Test
    fun `init EXPECT current step is 6`() {
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
    fun `init EXPECT default selected payer is RECEIVER`() {
        val viewModel = createViewModel()

        val actual = viewModel.state.value.content.selectedPayer

        assertEquals(Payer.RECEIVER, actual)
    }

    @Test
    fun `init EXPECT payers list contains all entries`() {
        val viewModel = createViewModel()

        val actual = viewModel.state.value.content.payers

        assertEquals(Payer.entries, actual)
    }

    // endregion

    // region SelectPayer

    @Test
    fun `select SENDER payer EXPECT selected payer is SENDER`() = runTest {
        val viewModel = createViewModel()

        viewModel.onIntent(PayerIntent.SelectPayer(Payer.SENDER))
        advanceUntilIdle()

        assertEquals(Payer.SENDER, viewModel.state.value.content.selectedPayer)
    }

    @Test
    fun `select RECEIVER payer after selecting SENDER EXPECT selected payer is RECEIVER`() = runTest {
        val viewModel = createViewModel()

        viewModel.onIntent(PayerIntent.SelectPayer(Payer.SENDER))
        viewModel.onIntent(PayerIntent.SelectPayer(Payer.RECEIVER))
        advanceUntilIdle()

        assertEquals(Payer.RECEIVER, viewModel.state.value.content.selectedPayer)
    }

    @Test
    fun `select payer EXPECT payers list unchanged`() = runTest {
        val viewModel = createViewModel()

        viewModel.onIntent(PayerIntent.SelectPayer(Payer.SENDER))
        advanceUntilIdle()

        assertEquals(Payer.entries, viewModel.state.value.content.payers)
    }

    // endregion

    // region Back

    @Test
    fun `back EXPECT router back called`() = runTest {
        val viewModel = createViewModel()

        viewModel.onIntent(PayerIntent.Back)
        advanceUntilIdle()

        verify { router.back() }
    }

    // endregion

    // region ClickContinue

    @Test
    fun `click continue with default payer EXPECT repository set payer called with RECEIVER`() = runTest {
        val viewModel = createViewModel()

        viewModel.onIntent(PayerIntent.ClickContinue)
        advanceUntilIdle()

        coVerify { payerRepository.setPayer(Payer.RECEIVER) }
    }

    @Test
    fun `click continue after selecting SENDER EXPECT repository set payer called with SENDER`() = runTest {
        val viewModel = createViewModel()

        viewModel.onIntent(PayerIntent.SelectPayer(Payer.SENDER))
        viewModel.onIntent(PayerIntent.ClickContinue)
        advanceUntilIdle()

        coVerify { payerRepository.setPayer(Payer.SENDER) }
    }

    // endregion
}
