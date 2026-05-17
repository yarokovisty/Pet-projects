package org.yarokovisty.delivery.feature.delivery.person.navigation

import androidx.navigation3.runtime.EntryProviderScope
import org.yarokovisty.delivery.feature.delivery.person.ui.screen.ReceiverScreen
import org.yarokovisty.delivery.feature.delivery.person.ui.screen.SenderScreen
import org.yarokovisty.delivery.libs.navigation.destination.Screen

fun EntryProviderScope<Screen>.receiverEntry() {
    entry<ReceiverDestination> {
        ReceiverScreen(screenType = it.type)
    }
}

fun EntryProviderScope<Screen>.senderEntry() {
    entry<SenderDestination> {
        SenderScreen(screenType = it.type)
    }
}
