package org.pet.project.rickandmorty.app.navigation.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.pet.project.rickandmorty.app.navigation.main.MainNavigator
import org.pet.project.rickandmorty.core.navigation.LocalNestedNavController
import org.pet.project.rickandmorty.core.navigation.NestedNavController
import org.pet.project.rickandmorty.core.navigation.Tab

internal class MainNavigatorImpl(
    private val nestedNavController: NestedNavController
) : MainNavigator {

    override fun openTab(tab: Tab) {
        nestedNavController.navigateToTab(tab)
    }
}

@Composable
internal fun rememberMainNavigator(): MainNavigator {
    val nestedNavController = LocalNestedNavController.current
    return remember {
        MainNavigatorImpl(nestedNavController)
    }
}