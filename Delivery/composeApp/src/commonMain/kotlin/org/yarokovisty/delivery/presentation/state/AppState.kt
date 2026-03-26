package org.yarokovisty.delivery.presentation.state

import org.yarokovisty.delivery.common.presentation.State
import org.yarokovisty.delivery.libs.navigation.destination.Screen

data class AppState(
    val backStack: List<Screen>
) : State {

    companion object {

        fun initial(backStack: List<Screen>) =
            AppState(backStack)
    }
}
