package org.pet.project.rickandmorty.feature.character.impl.navigation

import androidx.compose.runtime.staticCompositionLocalOf

val LocalCharacterListNavigator = staticCompositionLocalOf<CharacterListNavigator> {
    error("CharacterNavigator not provided")
}