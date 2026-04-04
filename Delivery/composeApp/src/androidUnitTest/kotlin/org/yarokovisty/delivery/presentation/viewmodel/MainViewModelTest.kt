package org.yarokovisty.delivery.presentation.viewmodel

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.yarokovisty.delivery.common.auth.domain.usecase.IsUserAuthorizedUseCase
import org.yarokovisty.delivery.feature.delivery.main.api.navigation.DeliveryTab
import org.yarokovisty.delivery.feature.login.api.navigation.LoginDestination
import org.yarokovisty.delivery.feature.profile.main.api.navigation.ProfileTab
import org.yarokovisty.delivery.libs.navigation.backstack.BottomBarBackStack
import org.yarokovisty.delivery.libs.navigation.backstack.GlobalBackStack
import org.yarokovisty.delivery.navigation.HistoryTab
import org.yarokovisty.delivery.navigation.MainDestination
import org.yarokovisty.delivery.presentation.intent.MainIntent
import org.yarokovisty.delivery.presentation.router.MainRouter
import org.yarokovisty.delivery.presentation.state.MainState
import org.yarokovisty.delivery.presentation.state.MainTab
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private val isUserAuthorizedUseCase: IsUserAuthorizedUseCase = mockk()
    private val bottomBarBackStack = BottomBarBackStack(DeliveryTab)
    private val globalBackStack = GlobalBackStack(MainDestination)
    private val router = MainRouter(globalBackStack, bottomBarBackStack)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init EXPECT initial state`() {
        val expected = MainState.initial(bottomBarBackStack.backStack)
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
        coEvery { isUserAuthorizedUseCase() } returns true
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
        coEvery { isUserAuthorizedUseCase() } returns true
        val expected = MainTab.DELIVERY
        val viewModel = createViewModel()

        viewModel.onIntent(MainIntent.SwitchTab(MainTab.PROFILE))
        viewModel.onIntent(MainIntent.Back)
        val actual = viewModel.state.value.selectedTab

        assertEquals(expected, actual)
    }

    @Test
    fun `switch to history and profile tabs then back twice EXPECT state updated to delivery`() {
        coEvery { isUserAuthorizedUseCase() } returns true
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
        coEvery { isUserAuthorizedUseCase() } returns true
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

    @Test
    fun `switch to profile tab when authorized EXPECT profile tab opened`() {
        coEvery { isUserAuthorizedUseCase() } returns true
        val viewModel = createViewModel()

        viewModel.onIntent(MainIntent.SwitchTab(MainTab.PROFILE))

        assertEquals(ProfileTab.startDestination, bottomBarBackStack.backStack.last())
    }

    @Test
    fun `switch to profile tab when authorized EXPECT check authorization`() {
        coEvery { isUserAuthorizedUseCase() } returns true
        val viewModel = createViewModel()

        viewModel.onIntent(MainIntent.SwitchTab(MainTab.PROFILE))

        coVerify { isUserAuthorizedUseCase() }
    }

    @Test
    fun `switch to profile tab when not authorized EXPECT login screen opened`() {
        coEvery { isUserAuthorizedUseCase() } returns false
        val viewModel = createViewModel()

        viewModel.onIntent(MainIntent.SwitchTab(MainTab.PROFILE))

        assertEquals(LoginDestination, globalBackStack.backStack.last())
    }

    @Test
    fun `switch to profile tab when not authorized EXPECT check authorization`() {
        coEvery { isUserAuthorizedUseCase() } returns false
        val viewModel = createViewModel()

        viewModel.onIntent(MainIntent.SwitchTab(MainTab.PROFILE))

        coVerify { isUserAuthorizedUseCase() }
    }

    @Test
    fun `switch to profile tab when not authorized EXPECT profile tab not added to backstack`() {
        coEvery { isUserAuthorizedUseCase() } returns false
        val viewModel = createViewModel()

        viewModel.onIntent(MainIntent.SwitchTab(MainTab.PROFILE))

        assertEquals(listOf(DeliveryTab.startDestination), bottomBarBackStack.backStack)
    }

    private fun createViewModel(): MainViewModel =
        MainViewModel(isUserAuthorizedUseCase, router)
}
