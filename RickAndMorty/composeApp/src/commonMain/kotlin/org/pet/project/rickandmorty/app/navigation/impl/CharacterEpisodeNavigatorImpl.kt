package org.pet.project.rickandmorty.app.navigation.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.pet.project.rickandmorty.core.navigation.GlobalNavController
import org.pet.project.rickandmorty.core.navigation.LocalGlobalNavController
import org.pet.project.rickandmorty.feature.episode.navigation.CharacterEpisodeNavigator

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