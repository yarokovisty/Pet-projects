package org.pet.project.rickandmorty.app.navigation.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import org.pet.project.rickandmorty.core.navigation.back
import org.pet.project.rickandmorty.feature.character.impl.navigation.CharacterItemRoute
import org.pet.project.rickandmorty.feature.character.impl.navigation.CharacterNavigator
import org.pet.project.rickandmorty.feature.episode.navigation.CharacterEpisodeRoute
import org.pet.project.rickandmorty.feature.location.navigation.LocationItemRoute

class CharacterNavigatorImpl(
    private val globalNavController: NavHostController,
    private val innerNavController: NavHostController
) : CharacterNavigator {

    override fun openCharacterItemScreen(characterId: Int) {
        innerNavController.navigate(CharacterItemRoute(characterId))
    }

    override fun openLocationScreen(locationName: String) {
        globalNavController.navigate(LocationItemRoute(locationName))
    }

    override fun openCharacterEpisodeScreen(characterId: Int) {
        globalNavController.navigate(CharacterEpisodeRoute(characterId))
    }

    override fun back() {
        innerNavController.back()
    }
}

@Composable
fun rememberCharacterNavigator(
    globalNavController: NavHostController,
    innerNavController: NavHostController,
) : CharacterNavigator {
    return remember {
        CharacterNavigatorImpl(globalNavController, innerNavController)
    }
}