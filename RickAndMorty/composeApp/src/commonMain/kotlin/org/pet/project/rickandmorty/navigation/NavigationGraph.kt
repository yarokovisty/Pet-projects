package org.pet.project.rickandmorty.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavigationGraph(
    navController: NavHostController,
    modifier: Modifier,
    characterListScreen: @Composable () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Characters,
        modifier = modifier
    ) {
        composable<Screen.Characters> { characterListScreen() }
        composable<Screen.Episodes> {  }
        composable<Screen.Search> {  }
    }

}