package org.yarokovisty.delivery.feature.login.navigation

import androidx.navigation3.runtime.EntryProviderScope
import org.yarokovisty.delivery.feature.login.ui.screen.LoginScreen
import org.yarokovisty.delivery.libs.navigation.destination.Screen

fun EntryProviderScope<Screen>.loginEntry() {
    entry<LoginDestination> {
        LoginScreen()
    }
}
