package org.yarokovisty.delivery.presentation.state

import org.yarokovisty.delivery.common.presentation.State
import org.yarokovisty.delivery.libs.navigation.destination.Screen
import org.yarokovisty.delivery.navigation.MainDestination

data class AppState(
    val backStack: List<Screen>
) : State {

    companion object {
        val INITIAL = AppState(backStack = listOf(MainDestination))
    }
}
