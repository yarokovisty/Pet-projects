package org.pet.project.rickandmorty.feature.episode.impl.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.pet.project.rickandmorty.core.navigation.navigator.GlobalNavigator
import org.pet.project.rickandmorty.core.navigation.navigator.LocalGlobalNavigator
import org.pet.project.rickandmorty.core.navigation.navigator.Navigator
import org.pet.project.rickandmorty.navigation.ksp.annotation.ScreenNavigator

@ScreenNavigator
class CharacterEpisodeNavigator(private val globalNavigator: GlobalNavigator) : Navigator {

    fun back() {
        globalNavigator.back()
    }
}