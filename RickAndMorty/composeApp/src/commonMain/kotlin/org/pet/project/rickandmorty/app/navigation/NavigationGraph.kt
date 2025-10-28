package org.pet.project.rickandmorty.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.pet.project.rickandmorty.app.navigation.impl.rememberCharacterNavigator
import org.pet.project.rickandmorty.app.ui.screen.MainScreen
import org.pet.project.rickandmorty.feature.character.navigation.CharacterGraph
import org.pet.project.rickandmorty.feature.character.navigation.characterGraph
import org.pet.project.rickandmorty.feature.location.navigation.LocationItemRoute
import org.pet.project.rickandmorty.feature.location.ui.screen.LocationItemScreen

@Composable
fun GlobalNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = MainRoute
    ) {
        composable<MainRoute> {
            MainScreen(navController)
        }
        composable<LocationItemRoute> {
            LocationItemScreen()
        }
    }
}

@Composable
fun InnerNavigationGraph(
    globalNavController: NavHostController,
    innerNavController: NavHostController,
    modifier: Modifier = Modifier
) {
    val characterNavigator = rememberCharacterNavigator(globalNavController, innerNavController)

    NavHost(
        navController = innerNavController,
        startDestination = CharacterGraph,
        modifier = modifier
    ) {
        characterGraph(characterNavigator)
    }
}
