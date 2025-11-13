package org.pet.project.rickandmorty.feature.character.navigation

import androidx.compose.runtime.staticCompositionLocalOf

val LocalCharacterNavigator = staticCompositionLocalOf<CharacterNavigator> {
    error("CharacterNavigator not provided")
}