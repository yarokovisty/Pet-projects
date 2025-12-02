package org.pet.project.rickandmorty.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class GlobalNavController(val navController: NavHostController) {

    fun navigate(route: Route) {
        navController.navigate(route)
    }

    fun back() {
        navController.popBackStack()
    }
}

@Composable
fun rememberGlobalNavController(
    navHostController: NavHostController = rememberNavController()
): GlobalNavController {
    return remember {
        GlobalNavController(navHostController)
    }
}

val LocalGlobalNavController = staticCompositionLocalOf<GlobalNavController> {
    error("GlobalNavController not provided")
}