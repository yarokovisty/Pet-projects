package org.pet.project.rickandmorty.feature.episode.impl.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.pet.project.rickandmorty.core.navigation.navigator.GlobalNavigator
import org.pet.project.rickandmorty.core.navigation.navigator.LocalGlobalNavigator
import org.pet.project.rickandmorty.core.navigation.navigator.Navigator

class CharacterEpisodeNavigator(private val globalNavigator: GlobalNavigator) : Navigator {

    fun back() {
        globalNavigator.back()
    }
}

@Composable
internal fun rememberCharacterEpisodeNavigator(): CharacterEpisodeNavigator {
    val globalNavigator = LocalGlobalNavigator.current

    return remember { CharacterEpisodeNavigator(globalNavigator) }
}