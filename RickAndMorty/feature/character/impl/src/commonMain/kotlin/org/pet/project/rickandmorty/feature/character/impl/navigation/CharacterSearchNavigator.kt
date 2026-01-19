package org.pet.project.rickandmorty.feature.character.impl.navigation

import org.pet.project.rickandmorty.core.navigation.navigator.Navigator
import org.pet.project.rickandmorty.core.navigation.navigator.NestedNavigator
import org.pet.project.rickandmorty.feature.character.api.navigation.CharacterItemRoute
import org.pet.project.rickandmorty.navigation.ksp.annotation.ScreenNavigator

@ScreenNavigator
internal class CharacterSearchNavigator(private val nestedNavigator: NestedNavigator) : Navigator {

    fun openCharacterItemScreen(characterId: Int) {
        nestedNavigator.navigate(CharacterItemRoute(characterId))
    }
}