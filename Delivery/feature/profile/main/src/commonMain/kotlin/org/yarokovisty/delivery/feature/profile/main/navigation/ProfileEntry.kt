package org.yarokovisty.delivery.feature.profile.main.navigation

import androidx.navigation3.runtime.EntryProviderScope
import org.yarokovisty.delivery.feature.profile.main.ui.screen.ProfileScreen
import org.yarokovisty.delivery.libs.navigation.destination.Screen

fun EntryProviderScope<Screen>.profileEntry() {
    entry<ProfileMainDestination> {
        ProfileScreen()
    }
}
