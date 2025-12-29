package org.pet.project.rickandmorty.feature.episode.impl.navigation

import androidx.compose.runtime.staticCompositionLocalOf
import org.pet.project.rickandmorty.core.navigation.navigator.GlobalNavigator
import org.pet.project.rickandmorty.core.navigation.navigator.Navigator

class CharacterEpisodeNavigator(private val globalNavigator: GlobalNavigator) : Navigator {

    fun back() {
        globalNavigator.back()
    }
}

val LocalCharacterEpisodeNavigator = staticCompositionLocalOf<CharacterEpisodeNavigator> {
    error("EpisodeNavigator not provided")
}