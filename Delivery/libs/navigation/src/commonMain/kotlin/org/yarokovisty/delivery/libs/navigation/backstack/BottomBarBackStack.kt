package org.yarokovisty.delivery.libs.navigation.backstack

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import org.yarokovisty.delivery.libs.navigation.destination.Destination
import org.yarokovisty.delivery.libs.navigation.destination.Screen
import org.yarokovisty.delivery.libs.navigation.destination.Tab
import kotlin.collections.get
import kotlin.collections.set

class BottomBarBackStack(startTab: Tab) {

    private val tabContainer: LinkedHashMap<Tab, SnapshotStateList<Screen>> = linkedMapOf(
        startTab to mutableStateListOf(startTab.startDestination)
    )

    var currentTab by mutableStateOf(startTab)
        private set

    private val currentTabBackstack: SnapshotStateList<Screen>
        get() = tabContainer[currentTab] ?: error("Current branch is null")

    val backStack: SnapshotStateList<Screen> = mutableStateListOf(startTab.startDestination)

    fun push(destination: Destination) {
        when (destination) {
            is Tab -> pushTab(destination)
            is Screen -> pushScreen(destination)
        }
    }

    private fun pushTab(tab: Tab) {
        if (tab !in tabContainer) {
            tabContainer[tab] = mutableStateListOf(tab.startDestination)
        } else {
            val stack = tabContainer.remove(tab)
            stack?.let { tabContainer[tab] = it }
        }

        currentTab = tab
        updateBranchBackstack()
    }

    private fun pushScreen(screen: Screen) {
        currentTabBackstack.add(screen)
        updateBranchBackstack()
    }

    fun pop() {
        when {
            currentTabBackstack.size > 1 -> {
                currentTabBackstack.removeLast()
                updateBranchBackstack()
            }

            tabContainer.size > 1 -> {
                tabContainer.remove(currentTab)
                currentTab = tabContainer.keys.last()
                updateBranchBackstack()
            }
        }
    }

    private fun updateBranchBackstack() {
        backStack.apply {
            clear()
            addAll(tabContainer.values.flatten())
        }
    }
}
