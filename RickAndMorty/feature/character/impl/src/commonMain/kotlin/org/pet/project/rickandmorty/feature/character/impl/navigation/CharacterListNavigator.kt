package org.pet.project.rickandmorty.feature.character.impl.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import org.pet.project.rickandmorty.core.navigation.LocalNestedNavController
import org.pet.project.rickandmorty.core.navigation.NestedNavController
import org.pet.project.rickandmorty.feature.character.api.navigation.CharacterItemRoute

interface CharacterListNavigator {

    fun openCharacterItemScreen(characterId: Int)
}

class CharacterListNavigatorImpl(
    private val nestedNavController: NestedNavController
) : CharacterListNavigator {

    override fun openCharacterItemScreen(characterId: Int) {
        nestedNavController.navigate(CharacterItemRoute(characterId))
    }
}

val LocalCharacterListNavigator = staticCompositionLocalOf<CharacterListNavigator> {
    error("CharacterNavigator not provided")
}

@Composable
fun rememberCharacterListNavigator() : CharacterListNavigator {
    val nestedNavController = LocalNestedNavController.current
    return remember {
        CharacterListNavigatorImpl(nestedNavController)
    }
}