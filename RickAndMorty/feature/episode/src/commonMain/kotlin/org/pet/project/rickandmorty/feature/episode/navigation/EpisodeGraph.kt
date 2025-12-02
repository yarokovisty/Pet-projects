package org.pet.project.rickandmorty.feature.episode.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import org.pet.project.rickandmorty.feature.episode.ui.screen.AllEpisodesScreen
import org.pet.project.rickandmorty.feature.episode.ui.screen.CharacterEpisodeScreen

fun NavGraphBuilder.characterEpisodeScreen() {
    composable<CharacterEpisodeRoute> {  backStackEntry  ->
        val characterId = backStackEntry.toRoute<CharacterEpisodeRoute>().characterId
        CharacterEpisodeScreen(characterId)
    }
}

fun NavGraphBuilder.episodeGraph() {
    navigation<EpisodeTab>(AllEpisodesRoute) {
        composable<AllEpisodesRoute> {
            AllEpisodesScreen()
        }
    }
}