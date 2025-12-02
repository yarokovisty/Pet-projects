package org.pet.project.rickandmorty.feature.character.impl.navigation

import androidx.compose.runtime.staticCompositionLocalOf

interface CharacterListNavigator {

    fun openCharacterItemScreen(characterId: Int)
}

val LocalCharacterListNavigator = staticCompositionLocalOf<CharacterListNavigator> {
    error("CharacterNavigator not provided")
}