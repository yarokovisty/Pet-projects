package org.yarokovisty.delivery.feature.delivery.main.navigation

import androidx.navigation3.runtime.EntryProviderScope
import org.yarokovisty.delivery.feature.delivery.main.ui.screen.DeliveryMainScreen
import org.yarokovisty.delivery.libs.navigation.destination.Screen

fun EntryProviderScope<Screen>.deliveryMainEntry() {
    entry<DeliveryMainDestination> {
        DeliveryMainScreen()
    }
}
