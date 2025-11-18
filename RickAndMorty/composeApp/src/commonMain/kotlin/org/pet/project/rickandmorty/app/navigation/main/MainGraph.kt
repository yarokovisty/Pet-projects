package org.pet.project.rickandmorty.app.navigation.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import org.pet.project.rickandmorty.app.ui.screen.MainScreen

fun NavGraphBuilder.mainGraph(navController: NavHostController) {
    composable<MainRoute> {
        MainScreen(navController)
    }
}