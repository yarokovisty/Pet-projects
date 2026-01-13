package org.pet.project.rickandmorty.feature.character.impl.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.pet.project.rickandmorty.core.navigation.navigator.GlobalNavigator
import org.pet.project.rickandmorty.core.navigation.navigator.LocalGlobalNavigator
import org.pet.project.rickandmorty.core.navigation.navigator.LocalNestedNavigator
import org.pet.project.rickandmorty.core.navigation.navigator.Navigator
import org.pet.project.rickandmorty.core.navigation.navigator.NestedNavigator
import org.pet.project.rickandmorty.feature.episode.api.navigation.CharacterEpisodeRoute
import org.pet.project.rickandmorty.feature.location.api.navigation.LocationItemRoute

internal class CharacterItemNavigator(
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

@Composable
internal fun rememberCharacterItemNavigator(): CharacterItemNavigator {
    val globalNavigator = LocalGlobalNavigator.current
    val nestedNavigator = LocalNestedNavigator.current

    return remember { CharacterItemNavigator(globalNavigator, nestedNavigator) }
}