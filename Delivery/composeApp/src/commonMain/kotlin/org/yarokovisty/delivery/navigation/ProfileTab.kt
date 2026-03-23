package org.yarokovisty.delivery.navigation

import kotlinx.serialization.Serializable
import org.yarokovisty.delivery.libs.navigation.destination.Screen
import org.yarokovisty.delivery.libs.navigation.destination.Tab

@Serializable
data object ProfileTab : Tab {

    override val startDestination: Screen = ProfileMainDestination
}
