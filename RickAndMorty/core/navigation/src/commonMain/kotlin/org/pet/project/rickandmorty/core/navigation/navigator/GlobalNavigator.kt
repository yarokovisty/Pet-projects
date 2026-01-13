package org.pet.project.rickandmorty.core.navigation.navigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.pet.project.rickandmorty.core.navigation.destination.Route
import org.pet.project.rickandmorty.navigation.ksp.annotation.ScreenNavigator

@ScreenNavigator
class GlobalNavigator(val navController: NavHostController) {

    fun navigate(route: Route) {
        navController.navigate(route)
    }

    fun back() {
        navController.popBackStack()
    }
}

@Composable
fun rememberGlobalNavigator(navHostController: NavHostController = rememberNavController()): GlobalNavigator {
    return remember { GlobalNavigator(navHostController) }
}