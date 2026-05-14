package org.yarokovisty.delivery.feature.delivery.payer.navigation

import androidx.navigation3.runtime.EntryProviderScope
import org.yarokovisty.delivery.feature.delivery.payer.ui.screen.PayerScreen
import org.yarokovisty.delivery.libs.navigation.destination.Screen

fun EntryProviderScope<Screen>.payerEntry() {
    entry<PayerDestination> {
        PayerScreen()
    }
}
