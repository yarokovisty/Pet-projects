package org.yarokovisty.delivery.presentation.viewmodel

import androidx.compose.runtime.snapshots.Snapshot
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.yarokovisty.delivery.common.auth.domain.usecase.IsUserAuthorizedUseCase
import org.yarokovisty.delivery.feature.delivery.main.navigation.DeliveryTab
import org.yarokovisty.delivery.feature.login.navigation.LoginDestination
import org.yarokovisty.delivery.feature.profile.main.navigation.ProfileTab
import org.yarokovisty.delivery.libs.navigation.backstack.BottomBarBackStack
import org.yarokovisty.delivery.libs.navigation.backstack.GlobalBackStack
import org.yarokovisty.delivery.navigation.destination.HistoryTab
import org.yarokovisty.delivery.navigation.destination.MainDestination
import org.yarokovisty.delivery.presentation.intent.MainIntent
import org.yarokovisty.delivery.presentation.router.MainRouter
import org.yarokovisty.delivery.presentation.state.MainState
import org.yarokovisty.delivery.presentation.state.MainTab
import org.yarokovisty.delivery.util.unitTest.MainDispatcherRule
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private val isUserAuthorizedUseCase: IsUserAuthorizedUseCase = mockk()
    private val bottomBarBackStack = BottomBarBackStack(DeliveryTab)
    private val globalBackStack = GlobalBackStack(MainDestination)
    private val router = MainRouter(globalBackStack, bottomBarBackStack)

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `init EXPECT initial state`() = runTest {
        val expected = MainState.initial(bottomBarBackStack.backStack)
        val viewModel = createViewModel()
        applySnapshot()

        val actual = viewModel.state.value

        assertEquals(expected, actual)
    }

    @Test
    fun `switch to delivery tab EXPECT state updated to delivery`() = runTest {
        val expected = MainTab.DELIVERY
        val viewModel = createViewModel()
        applySnapshot()

        viewModel.onIntent(MainIntent.SwitchTab(MainTab.DELIVERY))
        applySnapshot()
        val actual = viewModel.state.value.selectedTab

        assertEquals(expected, actual)
    }

    @Test
    fun `switch to history tab EXPECT state updated to history`() = runTest {
        val expected = MainTab.HISTORY
        val viewModel = createViewModel()
        applySnapshot()

        viewModel.onIntent(MainIntent.SwitchTab(MainTab.HISTORY))
        applySnapshot()
        val actual = viewModel.state.value.selectedTab

        assertEquals(expected, actual)
    }

    @Test
    fun `switch to profile tab EXPECT state updated to profile`() = runTest {
        coEvery { isUserAuthorizedUseCase() } returns true
        val expected = MainTab.PROFILE
        val viewModel = createViewModel()
        applySnapshot()

        viewModel.onIntent(MainIntent.SwitchTab(MainTab.PROFILE))
        applySnapshot()
        applySnapshot()
        val actual = viewModel.state.value.selectedTab

        assertEquals(expected, actual)
    }

    @Test
    fun `back from delivery tab EXPECT state remains delivery`() = runTest {
        val expected = MainTab.DELIVERY
        val viewModel = createViewModel()
        applySnapshot()

        viewModel.onIntent(MainIntent.Back)
        applySnapshot()
        val actual = viewModel.state.value.selectedTab

        assertEquals(expected, actual)
    }

    @Test
    fun `switch to history then back EXPECT state updated to delivery`() = runTest {
        val expected = MainTab.DELIVERY
        val viewModel = createViewModel()
        applySnapshot()

        viewModel.onIntent(MainIntent.SwitchTab(MainTab.HISTORY))
        applySnapshot()
        viewModel.onIntent(MainIntent.Back)
        applySnapshot()
        val actual = viewModel.state.value.selectedTab

        assertEquals(expected, actual)
    }

    @Test
    fun `switch to profile then back EXPECT state updated to delivery`() = runTest {
        coEvery { isUserAuthorizedUseCase() } returns true
        val expected = MainTab.DELIVERY
        val viewModel = createViewModel()
        applySnapshot()

        viewModel.onIntent(MainIntent.SwitchTab(MainTab.PROFILE))
        applySnapshot()
        applySnapshot()
        viewModel.onIntent(MainIntent.Back)
        applySnapshot()
        val actual = viewModel.state.value.selectedTab

        assertEquals(expected, actual)
    }

    @Test
    fun `switch to history and profile tabs then back twice EXPECT state updated to delivery`() = runTest {
        coEvery { isUserAuthorizedUseCase() } returns true
        val expected = MainTab.DELIVERY
        val viewModel = createViewModel()
        applySnapshot()

        viewModel.onIntent(MainIntent.SwitchTab(MainTab.HISTORY))
        applySnapshot()
        viewModel.onIntent(MainIntent.SwitchTab(MainTab.PROFILE))
        applySnapshot()
        applySnapshot()
        viewModel.onIntent(MainIntent.Back)
        applySnapshot()
        viewModel.onIntent(MainIntent.Back)
        applySnapshot()
        val actual = viewModel.state.value.selectedTab

        assertEquals(expected, actual)
    }

    @Test
    fun `switch to delivery tab EXPECT backstack contains delivery destination`() = runTest {
        val expected = listOf(DeliveryTab.startDestination)
        val viewModel = createViewModel()
        applySnapshot()

        viewModel.onIntent(MainIntent.SwitchTab(MainTab.DELIVERY))
        applySnapshot()
        val actual = viewModel.state.value.backStack

        assertEquals(expected, actual)
    }

    @Test
    fun `switch to history tab EXPECT backstack contains both destinations`() = runTest {
        val expected = listOf(DeliveryTab.startDestination, HistoryTab.startDestination)
        val viewModel = createViewModel()
        applySnapshot()

        viewModel.onIntent(MainIntent.SwitchTab(MainTab.HISTORY))
        applySnapshot()
        val actual = viewModel.state.value.backStack

        assertEquals(expected, actual)
    }

    @Test
    fun `switch to all tabs EXPECT backstack contains all destinations`() = runTest {
        coEvery { isUserAuthorizedUseCase() } returns true
        val expected = listOf(
            DeliveryTab.startDestination,
            HistoryTab.startDestination,
            ProfileTab.startDestination
        )
        val viewModel = createViewModel()
        applySnapshot()

        viewModel.onIntent(MainIntent.SwitchTab(MainTab.HISTORY))
        applySnapshot()
        viewModel.onIntent(MainIntent.SwitchTab(MainTab.PROFILE))
        applySnapshot()
        applySnapshot()
        val actual = viewModel.state.value.backStack

        assertEquals(expected, actual)
    }

    @Test
    fun `switch to profile tab when authorized EXPECT profile tab opened`() = runTest {
        coEvery { isUserAuthorizedUseCase() } returns true
        val viewModel = createViewModel()
        applySnapshot()

        viewModel.onIntent(MainIntent.SwitchTab(MainTab.PROFILE))
        applySnapshot() // Execute the authorization coroutine
        applySnapshot() // Apply the snapshot state change

        assertEquals(ProfileTab.startDestination, bottomBarBackStack.backStack.last())
    }

    @Test
    fun `switch to profile tab when authorized EXPECT check authorization`() = runTest {
        coEvery { isUserAuthorizedUseCase() } returns true
        val viewModel = createViewModel()
        applySnapshot()

        viewModel.onIntent(MainIntent.SwitchTab(MainTab.PROFILE))
        applySnapshot()

        coVerify { isUserAuthorizedUseCase() }
    }

    @Test
    fun `switch to profile tab when not authorized EXPECT login screen opened`() = runTest {
        coEvery { isUserAuthorizedUseCase() } returns false
        val viewModel = createViewModel()
        applySnapshot()

        viewModel.onIntent(MainIntent.SwitchTab(MainTab.PROFILE))
        applySnapshot()

        assertEquals(LoginDestination, globalBackStack.backStack.last())
    }

    @Test
    fun `switch to profile tab when not authorized EXPECT check authorization`() = runTest {
        coEvery { isUserAuthorizedUseCase() } returns false
        val viewModel = createViewModel()
        applySnapshot()

        viewModel.onIntent(MainIntent.SwitchTab(MainTab.PROFILE))
        applySnapshot()

        coVerify { isUserAuthorizedUseCase() }
    }

    @Test
    fun `switch to profile tab when not authorized EXPECT profile tab not added to backstack`() = runTest {
        coEvery { isUserAuthorizedUseCase() } returns false
        val viewModel = createViewModel()
        applySnapshot()

        viewModel.onIntent(MainIntent.SwitchTab(MainTab.PROFILE))
        applySnapshot()

        assertEquals(listOf(DeliveryTab.startDestination), bottomBarBackStack.backStack)
    }

    private fun createViewModel(): MainViewModel =
        MainViewModel(isUserAuthorizedUseCase, router)

    private fun applySnapshot() {
        Snapshot.sendApplyNotifications()
        mainDispatcherRule.dispatcher.scheduler.advanceUntilIdle()
    }
}
