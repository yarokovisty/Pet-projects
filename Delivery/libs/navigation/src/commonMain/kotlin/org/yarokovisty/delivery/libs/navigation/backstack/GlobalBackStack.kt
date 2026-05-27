package org.yarokovisty.delivery.libs.navigation.backstack

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import org.yarokovisty.delivery.libs.navigation.destination.Screen

class GlobalBackStack(startScreen: Screen) {

    val backStack: SnapshotStateList<Screen> = mutableStateListOf(startScreen)

    fun push(screen: Screen) {
        backStack.add(screen)
    }

    fun replace(screen: Screen) {
        backStack.removeLastOrNull()
        backStack.add(screen)
    }

    fun pop() {
        backStack.removeLastOrNull()
    }

    fun popTo(screen: Screen, inclusive: Boolean = false) {
        val index = backStack.indexOf(screen)

        if (index == -1) return

        val removeFrom = if (inclusive) index else index + 1
        backStack.subList(removeFrom, backStack.size).clear()
    }

    fun newRoot(screen: Screen) {
        backStack.clear()
        backStack.add(screen)
    }

    fun newChain(vararg screens: Screen) {
        backStack.clear()
        backStack.addAll(screens.asList())
    }
}
