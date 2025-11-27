package org.pet.project.rickandmorty.feature.episode.navigation

import androidx.compose.runtime.staticCompositionLocalOf

val LocalCharacterEpisodeNavigator = staticCompositionLocalOf<CharacterEpisodeNavigator> {
    error("EpisodeNavigator not provided")
}