package org.yarokovisty.delivery.navigation.destination

import kotlinx.serialization.Serializable
import org.yarokovisty.delivery.libs.navigation.destination.Screen
import org.yarokovisty.delivery.libs.navigation.destination.Tab

@Serializable
data object HistoryTab : Tab {

    override val startDestination: Screen = HistoryMainDestination
}
