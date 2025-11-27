package org.pet.project.rickandmorty.app.navigation.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import org.pet.project.rickandmorty.feature.character.impl.navigation.CharacterItemRoute
import org.pet.project.rickandmorty.feature.character.impl.navigation.CharacterListNavigator

class CharacterListNavigatorImpl(
    private val innerNavController: NavHostController
) : CharacterListNavigator {

    override fun openCharacterItemScreen(characterId: Int) {
        innerNavController.navigate(CharacterItemRoute(characterId))
    }
}

@Composable
fun rememberCharacterListNavigator(
    innerNavController: NavHostController,
) : CharacterListNavigator {
    return remember {
        CharacterListNavigatorImpl(innerNavController)
    }
}