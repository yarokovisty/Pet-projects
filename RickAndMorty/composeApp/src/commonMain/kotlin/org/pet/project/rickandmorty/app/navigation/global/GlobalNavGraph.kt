package org.pet.project.rickandmorty.app.navigation.global

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import org.pet.project.rickandmorty.app.navigation.main.MainRoute
import org.pet.project.rickandmorty.app.navigation.main.mainGraph
import org.pet.project.rickandmorty.core.navigation.navigator.LocalGlobalNavigator
import org.pet.project.rickandmorty.feature.episode.impl.navigation.characterEpisodeScreen
import org.pet.project.rickandmorty.feature.location.impl.navigation.locationScreen

@Composable
fun GlobalNavGraph() {
    NavHost(
        navController = LocalGlobalNavigator.current.navController,
        startDestination = MainRoute
    ) {
        mainGraph()
        locationScreen()
        characterEpisodeScreen()
    }
}