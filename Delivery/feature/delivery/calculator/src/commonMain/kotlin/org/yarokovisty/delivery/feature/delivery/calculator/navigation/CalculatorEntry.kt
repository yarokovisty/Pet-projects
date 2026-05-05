package org.yarokovisty.delivery.feature.delivery.calculator.navigation

import androidx.navigation3.runtime.EntryProviderScope
import org.yarokovisty.delivery.feature.delivery.calculator.ui.screen.CalculatorScreen
import org.yarokovisty.delivery.libs.navigation.destination.Screen

fun EntryProviderScope<Screen>.calculatorEntry() {
    entry<CalculatorDestination> { destination ->
        CalculatorScreen(
            parcelInfo = destination.parcelInfo,
            receiverPoint = destination.receiverPoint,
            senderPoint = destination.senderPoint
        )
    }
}
