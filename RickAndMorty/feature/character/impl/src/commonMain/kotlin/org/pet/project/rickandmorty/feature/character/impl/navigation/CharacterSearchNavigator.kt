package org.pet.project.rickandmorty.feature.character.impl.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import org.pet.project.rickandmorty.core.navigation.LocalNestedNavController
import org.pet.project.rickandmorty.core.navigation.NestedNavController
import org.pet.project.rickandmorty.feature.character.api.navigation.CharacterItemRoute

interface CharacterSearchNavigator {

    fun openCharacterItemScreen(characterId: Int)
}

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

val LocalCharacterSearchNavigator = staticCompositionLocalOf<CharacterSearchNavigator> {
    error("CharacterSearchNavigator not provided")
}