package org.pet.project.rickandmorty.app.navigation.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import org.pet.project.rickandmorty.core.navigation.back
import org.pet.project.rickandmorty.feature.episode.navigation.CharacterEpisodeNavigator

class CharacterEpisodeNavigatorImpl(
    private val globalNavController: NavHostController
) : CharacterEpisodeNavigator {

    override fun back() {
        globalNavController.back()
    }
}

@Composable
fun rememberEpisodeNavigator(
    globalNavController: NavHostController
): CharacterEpisodeNavigator {
    return remember {
        CharacterEpisodeNavigatorImpl(globalNavController)
    }
}