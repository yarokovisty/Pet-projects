package org.yarokovisty.delivery.feature.delivery.order.presentation.viewmodel

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.yarokovisty.delivery.common.auth.domain.usecase.IsUserAuthorizedUseCase
import org.yarokovisty.delivery.feature.delivery.order.navigation.SuccessOrderRouter
import org.yarokovisty.delivery.feature.delivery.order.presentation.intent.SuccessOrderIntent
import org.yarokovisty.delivery.feature.delivery.order.presentation.state.SuccessOrderState
import org.yarokovisty.delivery.util.unitTest.MainDispatcherRule
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
internal class SuccessOrderViewModelTest {

    private val isUserAuthorizedUseCase: IsUserAuthorizedUseCase = mockk()
    private val router: SuccessOrderRouter = mockk(relaxed = true)

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private fun createViewModel(): SuccessOrderViewModel =
        SuccessOrderViewModel(
            isUserAuthorizedUseCase = isUserAuthorizedUseCase,
            router = router
        )

    // region Init

    @Test
    fun `init EXPECT state is SuccessOrderState`() {
        val viewModel = createViewModel()

        val actual = viewModel.state.value

        assertEquals(SuccessOrderState, actual)
    }

    // endregion

    // region Back

    @Test
    fun `back EXPECT router back to main called`() = runTest {
        val viewModel = createViewModel()

        viewModel.onIntent(SuccessOrderIntent.Back)
        advanceUntilIdle()

        verify { router.backToMain() }
    }

    // endregion

    // region CheckStatus

    @Test
    fun `check status when user is authorized EXPECT router opens history main screen`() = runTest {
        coEvery { isUserAuthorizedUseCase() } returns true
        val viewModel = createViewModel()

        viewModel.onIntent(SuccessOrderIntent.CheckStatus)
        advanceUntilIdle()

        verify { router.openHistoryMainScreen() }
    }

    @Test
    fun `check status when user is authorized EXPECT router does not open login screen`() = runTest {
        coEvery { isUserAuthorizedUseCase() } returns true
        val viewModel = createViewModel()

        viewModel.onIntent(SuccessOrderIntent.CheckStatus)
        advanceUntilIdle()

        verify(exactly = 0) { router.openLoginScreen() }
    }

    @Test
    fun `check status when user is not authorized EXPECT router opens login screen`() = runTest {
        coEvery { isUserAuthorizedUseCase() } returns false
        val viewModel = createViewModel()

        viewModel.onIntent(SuccessOrderIntent.CheckStatus)
        advanceUntilIdle()

        verify { router.openLoginScreen() }
    }

    @Test
    fun `check status when user is not authorized EXPECT router does not open history main screen`() = runTest {
        coEvery { isUserAuthorizedUseCase() } returns false
        val viewModel = createViewModel()

        viewModel.onIntent(SuccessOrderIntent.CheckStatus)
        advanceUntilIdle()

        verify(exactly = 0) { router.openHistoryMainScreen() }
    }

    @Test
    fun `check status EXPECT is user authorized use case called`() = runTest {
        coEvery { isUserAuthorizedUseCase() } returns true
        val viewModel = createViewModel()

        viewModel.onIntent(SuccessOrderIntent.CheckStatus)
        advanceUntilIdle()

        coVerify { isUserAuthorizedUseCase() }
    }

    // endregion
}
