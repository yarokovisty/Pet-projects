package org.yarokovisty.delivery.navigation

import kotlinx.serialization.Serializable
import org.yarokovisty.delivery.feature.delivery.main.api.navigation.DeliveryMainDestination
import org.yarokovisty.delivery.libs.navigation.destination.Screen
import org.yarokovisty.delivery.libs.navigation.destination.Tab

@Serializable
data object DeliveryTab : Tab {

    override val startDestination: Screen = DeliveryMainDestination
}
