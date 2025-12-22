package org.pet.project.rickandmorty.app.navigation.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.pet.project.rickandmorty.core.navigation.LocalNestedNavController
import org.pet.project.rickandmorty.core.navigation.NestedNavController
import org.pet.project.rickandmorty.feature.character.api.navigation.CharacterItemRoute
import org.pet.project.rickandmorty.feature.character.impl.navigation.CharacterSearchNavigator

class CharacterSearchNavigatorImpl(
    private val nestedNavController: NestedNavController
) : CharacterSearchNavigator {

    override fun openCharacterItemScreen(characterId: Int) {
        nestedNavController.navigate(CharacterItemRoute(characterId))
    }
}

@Composable
fun rememberCharacterSearchNavigator(): CharacterSearchNavigator {
    val nestedNavController = LocalNestedNavController.current
    return remember {
        CharacterSearchNavigatorImpl(nestedNavController)
    }
}