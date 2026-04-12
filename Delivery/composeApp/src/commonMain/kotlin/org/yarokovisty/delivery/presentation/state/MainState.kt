package org.yarokovisty.delivery.presentation.state

import org.yarokovisty.delivery.core.common.presentation.State
import org.yarokovisty.delivery.libs.navigation.destination.Screen

data class MainState(
    val backStack: List<Screen>,
    val selectedTab: MainTab,
) : State {

    companion object {

        fun initial(backStack: List<Screen>) =
            MainState(backStack = backStack, selectedTab = MainTab.DELIVERY)
    }
}
