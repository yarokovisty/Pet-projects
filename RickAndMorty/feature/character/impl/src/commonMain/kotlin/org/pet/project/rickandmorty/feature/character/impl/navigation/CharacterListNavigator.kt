package org.pet.project.rickandmorty.feature.character.impl.navigation

import androidx.compose.runtime.staticCompositionLocalOf
import org.pet.project.rickandmorty.core.navigation.navigator.Navigator
import org.pet.project.rickandmorty.core.navigation.navigator.NestedNavigator
import org.pet.project.rickandmorty.feature.character.api.navigation.CharacterItemRoute

class CharacterListNavigator(private val nestedNavigator: NestedNavigator) : Navigator {

    fun openCharacterItemScreen(characterId: Int) {
        nestedNavigator.navigate(CharacterItemRoute(characterId))
    }
}

val LocalCharacterListNavigator = staticCompositionLocalOf<CharacterListNavigator> {
    error("CharacterNavigator not provided")
}