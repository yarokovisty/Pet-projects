package org.pet.project.rickandmorty.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


class NestedNavController(val navController: NavHostController) {
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
fun rememberNestedNavController(
    navHostController: NavHostController = rememberNavController()
): NestedNavController {
    return remember {
        NestedNavController(navHostController)
    }
}

val LocalNestedNavController = staticCompositionLocalOf<NestedNavController> {
    error("NestedNavController not provided")
}