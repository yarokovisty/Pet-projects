package org.yarokovisty.delivery.presentation.state

import org.yarokovisty.delivery.common.presentation.State
import org.yarokovisty.delivery.libs.navigation.destination.Screen
import org.yarokovisty.delivery.navigation.DeliveryTab

data class MainState(
    val backStack: List<Screen>,
    val selectedTab: MainTab,
) : State {

    companion object {

        val INITIAL = MainState(
            backStack = listOf(DeliveryTab.startDestination),
            selectedTab = MainTab.DELIVERY
        )
    }
}
