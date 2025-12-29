package org.pet.project.rickandmorty.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import org.pet.project.rickandmorty.app.navigation.main.MainRoute
import org.pet.project.rickandmorty.app.navigation.main.mainGraph
import org.pet.project.rickandmorty.core.navigation.navigator.LocalGlobalNavigator
import org.pet.project.rickandmorty.feature.episode.impl.navigation.CharacterEpisodeNavigator
import org.pet.project.rickandmorty.feature.episode.impl.navigation.LocalCharacterEpisodeNavigator
import org.pet.project.rickandmorty.feature.episode.impl.navigation.characterEpisodeScreen
import org.pet.project.rickandmorty.feature.location.impl.navigation.LocalLocationNavigator
import org.pet.project.rickandmorty.feature.location.impl.navigation.LocationNavigator
import org.pet.project.rickandmorty.feature.location.impl.navigation.locationScreen

@Composable
fun GlobalNavGraph() {
    val globalNavController = LocalGlobalNavigator.current

    CompositionLocalProvider(
        LocalLocationNavigator provides LocationNavigator(globalNavController),
        LocalCharacterEpisodeNavigator provides CharacterEpisodeNavigator(globalNavController)
    ) {
        NavHost(
            navController = globalNavController.navController,
            startDestination = MainRoute
        ) {
            mainGraph()
            locationScreen()
            characterEpisodeScreen()
        }
    }
}