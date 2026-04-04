package org.yarokovisty.delivery.presentation.viewmodel

import org.yarokovisty.delivery.common.auth.domain.usecase.IsUserAuthorizedUseCase
import org.yarokovisty.delivery.common.presentation.BaseViewModel
import org.yarokovisty.delivery.feature.delivery.main.api.navigation.DeliveryTab
import org.yarokovisty.delivery.feature.profile.main.api.navigation.ProfileTab
import org.yarokovisty.delivery.navigation.HistoryTab
import org.yarokovisty.delivery.presentation.intent.MainIntent
import org.yarokovisty.delivery.presentation.router.MainRouter
import org.yarokovisty.delivery.presentation.state.MainState
import org.yarokovisty.delivery.presentation.state.MainTab

class MainViewModel(
    private val isUserAuthorizedUseCase: IsUserAuthorizedUseCase,
    private val router: MainRouter
) : BaseViewModel<MainState, MainIntent, Nothing>(
    MainState.initial(router.bottomBarBackStack.backStack)
) {

    override fun onIntent(intent: MainIntent) {
        when (intent) {
            is MainIntent.SwitchTab -> switchTab(intent.tab)
            is MainIntent.Back -> back()
        }
    }

    private fun switchTab(tab: MainTab) {
        when (tab) {
            MainTab.DELIVERY -> router.openDeliveryTab()
            MainTab.HISTORY -> router.openHistoryTab()
            MainTab.PROFILE -> openProfileTab()
        }
        changeTab()
    }

    private fun openProfileTab() {
        launch {
            if (isUserAuthorizedUseCase()) {
                router.openProfileTab()
            } else {
                router.openLoginScreen()
            }
        }
    }

    private fun back() {
        router.back()
        changeTab()
    }

    private fun changeTab() {
        val navTab = router.bottomBarBackStack.currentTab
        val tab = when (navTab) {
            DeliveryTab -> MainTab.DELIVERY
            HistoryTab -> MainTab.HISTORY
            ProfileTab -> MainTab.PROFILE
            else -> error("Unsupported destination tab")
        }

        updateState {
            copy(selectedTab = tab)
        }
    }
}
