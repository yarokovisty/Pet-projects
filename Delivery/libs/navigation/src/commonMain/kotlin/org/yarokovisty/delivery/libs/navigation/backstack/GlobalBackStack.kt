package org.yarokovisty.delivery.libs.navigation.backstack

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import org.yarokovisty.delivery.libs.navigation.destination.Screen

class GlobalBackStack(startScreen: Screen) {

    val backStack: SnapshotStateList<Screen> = mutableStateListOf(startScreen)

    fun push(screen: Screen) {
        backStack.add(screen)
    }

    fun pop() {
        backStack.removeLastOrNull()
    }
}
