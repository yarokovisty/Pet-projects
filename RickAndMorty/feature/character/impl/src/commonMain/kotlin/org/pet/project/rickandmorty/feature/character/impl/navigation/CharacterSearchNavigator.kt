package org.pet.project.rickandmorty.feature.character.impl.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.pet.project.rickandmorty.core.navigation.navigator.LocalNestedNavigator
import org.pet.project.rickandmorty.core.navigation.navigator.Navigator
import org.pet.project.rickandmorty.core.navigation.navigator.NestedNavigator
import org.pet.project.rickandmorty.feature.character.api.navigation.CharacterItemRoute

internal class CharacterSearchNavigator(private val nestedNavigator: NestedNavigator) : Navigator {

    fun openCharacterItemScreen(characterId: Int) {
        nestedNavigator.navigate(CharacterItemRoute(characterId))
    }
}

@Composable
internal fun rememberCharacterSearchNavigator(): CharacterSearchNavigator {
    val nestedNavigator = LocalNestedNavigator.current

    return remember { CharacterSearchNavigator(nestedNavigator) }
}