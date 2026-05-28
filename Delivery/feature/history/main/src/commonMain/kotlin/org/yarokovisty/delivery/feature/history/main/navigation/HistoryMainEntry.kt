package org.yarokovisty.delivery.feature.history.main.navigation

import androidx.navigation3.runtime.EntryProviderScope
import org.yarokovisty.delivery.feature.history.main.ui.screen.HistoryMainScreen
import org.yarokovisty.delivery.libs.navigation.destination.Screen

fun EntryProviderScope<Screen>.historyMainEntry() {
    entry<HistoryMainDestination> {
        HistoryMainScreen()
    }
}
