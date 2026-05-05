package org.yarokovisty.delivery.feature.delivery.direction.navigation

import androidx.navigation3.runtime.EntryProviderScope
import org.yarokovisty.delivery.feature.delivery.direction.ui.screen.DirectionScreen
import org.yarokovisty.delivery.libs.navigation.destination.Screen

fun EntryProviderScope<Screen>.directionEntry() {
    entry<DirectionDestination> { destination ->
        DirectionScreen(destination.directionType)
    }
}
