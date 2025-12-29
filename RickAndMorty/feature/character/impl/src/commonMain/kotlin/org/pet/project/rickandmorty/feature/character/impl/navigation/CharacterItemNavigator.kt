package org.pet.project.rickandmorty.feature.character.impl.navigation

import androidx.compose.runtime.staticCompositionLocalOf
import org.pet.project.rickandmorty.core.navigation.navigator.GlobalNavigator
import org.pet.project.rickandmorty.core.navigation.navigator.Navigator
import org.pet.project.rickandmorty.core.navigation.navigator.NestedNavigator
import org.pet.project.rickandmorty.feature.episode.api.navigation.CharacterEpisodeRoute
import org.pet.project.rickandmorty.feature.location.api.navigation.LocationItemRoute

class CharacterItemNavigator(
    private val globalNavigator: GlobalNavigator,
    private val nestedNavigator: NestedNavigator
) : Navigator {

    fun openLocationScreen(locationName: String) {
        globalNavigator.navigate(LocationItemRoute(locationName))
    }

    fun openCharacterEpisodeScreen(characterId: Int) {
        globalNavigator.navigate(CharacterEpisodeRoute(characterId))
    }

    fun back() {
        nestedNavigator.back()
    }
}

val LocalCharacterItemNavigator = staticCompositionLocalOf<CharacterItemNavigator> {
    error("CharacterNavigator not provided")
}