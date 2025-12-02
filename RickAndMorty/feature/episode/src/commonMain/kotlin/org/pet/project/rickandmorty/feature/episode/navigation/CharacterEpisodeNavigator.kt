package org.pet.project.rickandmorty.feature.episode.navigation

import androidx.compose.runtime.staticCompositionLocalOf

interface CharacterEpisodeNavigator {

    fun back()
}

val LocalCharacterEpisodeNavigator = staticCompositionLocalOf<CharacterEpisodeNavigator> {
    error("EpisodeNavigator not provided")
}