package org.yarokovisty.delivery.presentation.viewmodel

import org.junit.Test
import org.yarokovisty.delivery.libs.navigation.backstack.BottomBarBackStack
import org.yarokovisty.delivery.navigation.DeliveryTab
import org.yarokovisty.delivery.navigation.HistoryTab
import org.yarokovisty.delivery.navigation.ProfileTab
import org.yarokovisty.delivery.presentation.intent.MainIntent
import org.yarokovisty.delivery.presentation.router.MainRouter
import org.yarokovisty.delivery.presentation.state.MainState
import org.yarokovisty.delivery.presentation.state.MainTab
import kotlin.test.assertEquals

class MainViewModelTest {

    private val bottomBarBackStack = BottomBarBackStack(DeliveryTab)
    private val router = MainRouter(bottomBarBackStack)

    @Test
    fun `init EXPECT initial state`() {
        val expected = MainState.INITIAL
        val viewModel = createViewModel()

        val actual = viewModel.state.value

        assertEquals(expected, actual)
    }

    @Test
    fun `switch to delivery tab EXPECT state updated to delivery`() {
        val expected = MainTab.DELIVERY
        val viewModel = createViewModel()

        viewModel.onIntent(MainIntent.SwitchTab(MainTab.DELIVERY))
        val actual = viewModel.state.value.selectedTab

        assertEquals(expected, actual)
    }

    @Test
    fun `switch to history tab EXPECT state updated to history`() {
        val expected = MainTab.HISTORY
        val viewModel = createViewModel()

        viewModel.onIntent(MainIntent.SwitchTab(MainTab.HISTORY))
        val actual = viewModel.state.value.selectedTab

        assertEquals(expected, actual)
    }

    @Test
    fun `switch to profile tab EXPECT state updated to profile`() {
        val expected = MainTab.PROFILE
        val viewModel = createViewModel()

        viewModel.onIntent(MainIntent.SwitchTab(MainTab.PROFILE))
        val actual = viewModel.state.value.selectedTab

        assertEquals(expected, actual)
    }

    @Test
    fun `back from delivery tab EXPECT state remains delivery`() {
        val expected = MainTab.DELIVERY
        val viewModel = createViewModel()

        viewModel.onIntent(MainIntent.Back)
        val actual = viewModel.state.value.selectedTab

        assertEquals(expected, actual)
    }

    @Test
    fun `switch to history then back EXPECT state updated to delivery`() {
        val expected = MainTab.DELIVERY
        val viewModel = createViewModel()

        viewModel.onIntent(MainIntent.SwitchTab(MainTab.HISTORY))
        viewModel.onIntent(MainIntent.Back)
        val actual = viewModel.state.value.selectedTab

        assertEquals(expected, actual)
    }

    @Test
    fun `switch to profile then back EXPECT state updated to delivery`() {
        val expected = MainTab.DELIVERY
        val viewModel = createViewModel()

        viewModel.onIntent(MainIntent.SwitchTab(MainTab.PROFILE))
        viewModel.onIntent(MainIntent.Back)
        val actual = viewModel.state.value.selectedTab

        assertEquals(expected, actual)
    }

    @Test
    fun `switch to history and profile tabs then back twice EXPECT state updated to delivery`() {
        val expected = MainTab.DELIVERY
        val viewModel = createViewModel()

        viewModel.onIntent(MainIntent.SwitchTab(MainTab.HISTORY))
        viewModel.onIntent(MainIntent.SwitchTab(MainTab.PROFILE))
        viewModel.onIntent(MainIntent.Back)
        viewModel.onIntent(MainIntent.Back)
        val actual = viewModel.state.value.selectedTab

        assertEquals(expected, actual)
    }

    @Test
    fun `switch to delivery tab EXPECT backstack contains delivery destination`() {
        val expected = listOf(DeliveryTab.startDestination)
        val viewModel = createViewModel()

        viewModel.onIntent(MainIntent.SwitchTab(MainTab.DELIVERY))
        val actual = viewModel.state.value.backStack

        assertEquals(expected, actual)
    }

    @Test
    fun `switch to history tab EXPECT backstack contains both destinations`() {
        val expected = listOf(DeliveryTab.startDestination, HistoryTab.startDestination)
        val viewModel = createViewModel()

        viewModel.onIntent(MainIntent.SwitchTab(MainTab.HISTORY))
        val actual = viewModel.state.value.backStack

        assertEquals(expected, actual)
    }

    @Test
    fun `switch to all tabs EXPECT backstack contains all destinations`() {
        val expected = listOf(
            DeliveryTab.startDestination,
            HistoryTab.startDestination,
            ProfileTab.startDestination
        )
        val viewModel = createViewModel()

        viewModel.onIntent(MainIntent.SwitchTab(MainTab.HISTORY))
        viewModel.onIntent(MainIntent.SwitchTab(MainTab.PROFILE))
        val actual = viewModel.state.value.backStack

        assertEquals(expected, actual)
    }

    private fun createViewModel(): MainViewModel =
        MainViewModel(router)
}
