package org.yarokovisty.delivery.feature.delivery.order.navigation

import androidx.navigation3.runtime.EntryProviderScope
import org.yarokovisty.delivery.feature.delivery.order.ui.screen.SuccessOrderScreen
import org.yarokovisty.delivery.libs.navigation.destination.Screen

fun EntryProviderScope<Screen>.successOrderEntry() {
    entry<SuccessOrderDestination> {
        SuccessOrderScreen()
    }
}
