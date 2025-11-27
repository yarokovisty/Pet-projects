package org.pet.project.rickandmorty.app.navigation.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import org.pet.project.rickandmorty.core.navigation.back
import org.pet.project.rickandmorty.feature.character.impl.navigation.CharacterItemNavigator
import org.pet.project.rickandmorty.feature.episode.navigation.CharacterEpisodeRoute
import org.pet.project.rickandmorty.feature.location.navigation.LocationItemRoute

class CharacterItemNavigatorImpl(
    private val globalNavController: NavHostController,
    private val innerNavController: NavHostController
) : CharacterItemNavigator {

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
) : CharacterItemNavigator {
    return remember {
        CharacterItemNavigatorImpl(globalNavController, innerNavController)
    }
}