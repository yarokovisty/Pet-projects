package org.yarokovisty.delivery.presentation.viewmodel

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import org.yarokovisty.delivery.libs.navigation.backstack.GlobalBackStack
import org.yarokovisty.delivery.libs.navigation.destination.Screen
import org.yarokovisty.delivery.navigation.MainDestination
import org.yarokovisty.delivery.presentation.intent.AppIntent
import org.yarokovisty.delivery.presentation.router.AppRouter
import org.yarokovisty.delivery.presentation.state.AppState
import kotlin.test.assertEquals

class AppViewModelTest {

    private val router: AppRouter = mockk()
    private val globalBackStack: GlobalBackStack = GlobalBackStack(MainDestination)

    @Test
    fun `init EXPECT initial state`() {
        val expected = AppState.INITIAL
        val viewModel = createViewModel()

        val actual = viewModel.state.value

        assertEquals(expected, actual)
    }

    @Test
    fun `back EXPECT router invoke back`() {
        every { router.back() } just Runs
        every { router.globalBackStack } returns globalBackStack
        val viewModel = createViewModel()

        viewModel.onIntent(AppIntent.Back)

        verify { router.back() }
    }

    @Test
    fun `back EXPECT state updated with backstack`() {
        val expected = emptyList<Screen>()
        val globalBackStack = GlobalBackStack(MainDestination)
        every { router.back() } answers { globalBackStack.pop() }
        every { router.globalBackStack } returns globalBackStack
        val viewModel = createViewModel()

        viewModel.onIntent(AppIntent.Back)

        val actual = viewModel.state.value.backStack
        assertEquals(expected, actual)
    }

    private fun createViewModel(): AppViewModel =
        AppViewModel(router)
}