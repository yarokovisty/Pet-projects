package org.yarokovisty.delivery.feature.delivery.point.navigation

import androidx.navigation3.runtime.EntryProviderScope
import org.yarokovisty.delivery.feature.delivery.point.ui.screen.ReceiverAddressScreen
import org.yarokovisty.delivery.feature.delivery.point.ui.screen.SenderAddressScreen
import org.yarokovisty.delivery.libs.navigation.destination.Screen

fun EntryProviderScope<Screen>.senderAddressEntry() {
    entry<SenderAddressDestination> {
        SenderAddressScreen(screenType = it.screenType)
    }
}

fun EntryProviderScope<Screen>.receiverAddressEntry() {
    entry<ReceiverAddressDestination> {
        ReceiverAddressScreen(screenType = it.screenType)
    }
}
