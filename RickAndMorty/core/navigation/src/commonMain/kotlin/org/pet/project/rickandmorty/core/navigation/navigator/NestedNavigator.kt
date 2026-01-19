package org.pet.project.rickandmorty.core.navigation.navigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.pet.project.rickandmorty.core.navigation.destination.Route
import org.pet.project.rickandmorty.core.navigation.destination.Tab

class NestedNavigator(val navController: NavHostController) {
    fun navigateToTab(tab: Tab) {
        navController.navigate(tab) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigate(route: Route) {
        navController.navigate(route)
    }

    fun back() {
        navController.popBackStack()
    }
}
@Composable
fun rememberNestedNavigator(navHostController: NavHostController = rememberNavController()): NestedNavigator {
    return remember { NestedNavigator(navHostController) }
}

val LocalNestedNavigator = staticCompositionLocalOf<NestedNavigator> {
    error("NestedNavigator not provided")
}