package org.pet.project.rickandmorty.app.navigation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.pet.project.rickandmorty.core.navigation.navigator.LocalNestedNavigator
import org.pet.project.rickandmorty.core.navigation.navigator.NestedNavigator
import org.pet.project.rickandmorty.core.navigation.destination.Tab

interface MainNavigator {

    fun openTab(tab: Tab)
}

internal class MainNavigatorImpl(
    private val nestedNavigator: NestedNavigator
) : MainNavigator {

    override fun openTab(tab: Tab) {
        nestedNavigator.navigateToTab(tab)
    }
}

@Composable
internal fun rememberMainNavigator(): MainNavigator {
    val nestedNavController = LocalNestedNavigator.current
    return remember {
        MainNavigatorImpl(nestedNavController)
    }
}