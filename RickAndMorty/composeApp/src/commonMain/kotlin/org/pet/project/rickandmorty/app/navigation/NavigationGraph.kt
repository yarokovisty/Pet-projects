package org.pet.project.rickandmorty.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import org.pet.project.rickandmorty.app.navigation.impl.rememberCharacterNavigator
import org.pet.project.rickandmorty.app.navigation.impl.rememberCharacterSearchNavigator
import org.pet.project.rickandmorty.app.navigation.impl.rememberEpisodeNavigator
import org.pet.project.rickandmorty.app.navigation.impl.rememberLocationNavigator
import org.pet.project.rickandmorty.app.navigation.main.MainRoute
import org.pet.project.rickandmorty.app.navigation.main.mainGraph
import org.pet.project.rickandmorty.core.navigation.LocalGlobalNavController
import org.pet.project.rickandmorty.core.navigation.LocalNestedNavController
import org.pet.project.rickandmorty.feature.character.api.navigation.CharacterTab
import org.pet.project.rickandmorty.feature.character.impl.navigation.LocalCharacterItemNavigator
import org.pet.project.rickandmorty.feature.character.impl.navigation.LocalCharacterListNavigator
import org.pet.project.rickandmorty.feature.character.impl.navigation.LocalCharacterSearchNavigator
import org.pet.project.rickandmorty.feature.character.impl.navigation.characterGraph
import org.pet.project.rickandmorty.feature.character.impl.navigation.characterSearchGraph
import org.pet.project.rickandmorty.feature.character.impl.navigation.rememberCharacterListNavigator
import org.pet.project.rickandmorty.feature.episode.navigation.LocalCharacterEpisodeNavigator
import org.pet.project.rickandmorty.feature.episode.navigation.characterEpisodeScreen
import org.pet.project.rickandmorty.feature.episode.navigation.episodeGraph
import org.pet.project.rickandmorty.feature.location.navigation.LocalLocationNavigator
import org.pet.project.rickandmorty.feature.location.navigation.locationScreen

@Composable
fun GlobalNavGraph() {
    val globalNavController = LocalGlobalNavController.current

    val locationNavigator = rememberLocationNavigator()
    val episodeNavigator = rememberEpisodeNavigator()

    CompositionLocalProvider(
        LocalLocationNavigator provides locationNavigator,
        LocalCharacterEpisodeNavigator provides episodeNavigator
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

@Composable
fun NestedNavGraph(modifier: Modifier = Modifier) {
    val nestedNavController = LocalNestedNavController.current
    val characterListNavigator = rememberCharacterListNavigator()
    val characterNavigator = rememberCharacterNavigator()
    val characterSearchNavigator = rememberCharacterSearchNavigator()

    CompositionLocalProvider(
        LocalCharacterListNavigator provides characterListNavigator,
        LocalCharacterItemNavigator provides characterNavigator,
        LocalCharacterSearchNavigator provides characterSearchNavigator
    ) {
        NavHost(
            navController = nestedNavController.navController,
            startDestination = CharacterTab,
            modifier = modifier
        ) {
            characterGraph()
            episodeGraph()
            characterSearchGraph()
        }
    }
}
