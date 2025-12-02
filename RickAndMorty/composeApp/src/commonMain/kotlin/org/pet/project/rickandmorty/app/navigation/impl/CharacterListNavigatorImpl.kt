package org.pet.project.rickandmorty.app.navigation.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import org.pet.project.rickandmorty.core.navigation.LocalNestedNavController
import org.pet.project.rickandmorty.feature.character.impl.navigation.CharacterItemRoute
import org.pet.project.rickandmorty.feature.character.impl.navigation.CharacterListNavigator

class CharacterListNavigatorImpl(
    private val nestedNavController: NavHostController
) : CharacterListNavigator {

    override fun openCharacterItemScreen(characterId: Int) {
        nestedNavController.navigate(CharacterItemRoute(characterId))
    }
}

@Composable
fun rememberCharacterListNavigator() : CharacterListNavigator {
    val nestedNavController = LocalNestedNavController.current.navController
    return remember {
        CharacterListNavigatorImpl(nestedNavController)
    }
}