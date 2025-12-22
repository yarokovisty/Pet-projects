package org.pet.project.rickandmorty.feature.episode.impl.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import org.pet.project.rickandmorty.core.navigation.GlobalNavController
import org.pet.project.rickandmorty.core.navigation.LocalGlobalNavController

interface CharacterEpisodeNavigator {

    fun back()
}

class CharacterEpisodeNavigatorImpl(
    private val globalNavController: GlobalNavController
) : CharacterEpisodeNavigator {

    override fun back() {
        globalNavController.back()
    }
}

@Composable
fun rememberEpisodeNavigator(): CharacterEpisodeNavigator {
    val globalNavController = LocalGlobalNavController.current
    return remember {
        CharacterEpisodeNavigatorImpl(globalNavController)
    }
}

val LocalCharacterEpisodeNavigator = staticCompositionLocalOf<CharacterEpisodeNavigator> {
    error("EpisodeNavigator not provided")
}