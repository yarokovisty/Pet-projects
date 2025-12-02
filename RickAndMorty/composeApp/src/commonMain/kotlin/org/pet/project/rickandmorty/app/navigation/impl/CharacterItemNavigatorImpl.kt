package org.pet.project.rickandmorty.app.navigation.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.pet.project.rickandmorty.core.navigation.GlobalNavController
import org.pet.project.rickandmorty.core.navigation.LocalGlobalNavController
import org.pet.project.rickandmorty.core.navigation.LocalNestedNavController
import org.pet.project.rickandmorty.core.navigation.NestedNavController
import org.pet.project.rickandmorty.feature.character.impl.navigation.CharacterItemNavigator
import org.pet.project.rickandmorty.feature.episode.navigation.CharacterEpisodeRoute
import org.pet.project.rickandmorty.feature.location.navigation.LocationItemRoute

class CharacterItemNavigatorImpl(
    private val globalNavController: GlobalNavController,
    private val nestedNavController: NestedNavController
) : CharacterItemNavigator {

    override fun openLocationScreen(locationName: String) {
        globalNavController.navigate(LocationItemRoute(locationName))
    }

    override fun openCharacterEpisodeScreen(characterId: Int) {
        globalNavController.navigate(CharacterEpisodeRoute(characterId))
    }

    override fun back() {
        nestedNavController.back()
    }
}

@Composable
fun rememberCharacterNavigator() : CharacterItemNavigator {
    val globalNavController = LocalGlobalNavController.current
    val nestedNavController = LocalNestedNavController.current

    return remember {
        CharacterItemNavigatorImpl(globalNavController, nestedNavController)
    }
}