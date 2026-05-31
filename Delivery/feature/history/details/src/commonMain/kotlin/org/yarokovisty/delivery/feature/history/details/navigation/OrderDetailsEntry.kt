package org.yarokovisty.delivery.feature.history.details.navigation

import androidx.navigation3.runtime.EntryProviderScope
import org.yarokovisty.delivery.feature.history.details.ui.screen.OrderDetailsScreen
import org.yarokovisty.delivery.libs.navigation.destination.Screen

fun EntryProviderScope<Screen>.orderDetailsEntry() {
    entry<OrderDetailsDestination> { destination ->
        val orderId = destination.orderId
        OrderDetailsScreen(orderId)
    }
}
