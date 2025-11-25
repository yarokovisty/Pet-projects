package org.pet.project.rickandmorty.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import org.pet.project.rickandmorty.app.navigation.impl.rememberCharacterNavigator
import org.pet.project.rickandmorty.app.navigation.impl.rememberLocationNavigator
import org.pet.project.rickandmorty.app.navigation.main.MainRoute
import org.pet.project.rickandmorty.app.navigation.main.mainGraph
import org.pet.project.rickandmorty.feature.character.impl.navigation.CharacterTab
import org.pet.project.rickandmorty.feature.character.impl.navigation.LocalCharacterNavigator
import org.pet.project.rickandmorty.feature.character.impl.navigation.characterGraph
import org.pet.project.rickandmorty.feature.episode.navigation.characterEpisodeScreen
import org.pet.project.rickandmorty.feature.location.navigation.LocalLocationNavigator
import org.pet.project.rickandmorty.feature.location.navigation.locationScreen

@Composable
fun GlobalNavGraph(
    navController: NavHostController
) {
    val locationNavigator = rememberLocationNavigator(navController)

    CompositionLocalProvider(
        LocalLocationNavigator provides locationNavigator
    ) {
        NavHost(
            navController = navController,
            startDestination = MainRoute
        ) {
            mainGraph(navController)
            locationScreen()
            characterEpisodeScreen()
        }
    }
}

@Composable
fun InnerNavGraph(
    globalNavController: NavHostController,
    innerNavController: NavHostController,
    modifier: Modifier = Modifier
) {
    val characterNavigator = rememberCharacterNavigator(globalNavController, innerNavController)

    CompositionLocalProvider(
        LocalCharacterNavigator provides characterNavigator
    ) {
        NavHost(
            navController = innerNavController,
            startDestination = CharacterTab,
            modifier = modifier
        ) {
            characterGraph()
        }
    }
}
