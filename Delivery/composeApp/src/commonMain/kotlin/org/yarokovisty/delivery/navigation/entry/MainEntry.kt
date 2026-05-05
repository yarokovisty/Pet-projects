package org.yarokovisty.delivery.navigation.entry

import androidx.navigation3.runtime.EntryProviderScope
import org.yarokovisty.delivery.libs.navigation.destination.Screen
import org.yarokovisty.delivery.navigation.destination.MainDestination
import org.yarokovisty.delivery.ui.screen.MainScreen

fun EntryProviderScope<Screen>.mainEntry() {
    entry<MainDestination> {
        MainScreen()
    }
}
