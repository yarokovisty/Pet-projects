package org.pet.project.rickandmorty.feature.character.impl.navigation

import androidx.compose.runtime.staticCompositionLocalOf

interface CharacterSearchNavigator {

    fun openCharacterItemScreen(characterId: Int)
}

val LocalCharacterSearchNavigator = staticCompositionLocalOf<CharacterSearchNavigator> {
    error("CharacterSearchNavigator not provided")
}