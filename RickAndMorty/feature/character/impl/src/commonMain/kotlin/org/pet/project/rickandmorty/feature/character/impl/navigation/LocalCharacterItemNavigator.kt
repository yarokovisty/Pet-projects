package org.pet.project.rickandmorty.feature.character.impl.navigation

import androidx.compose.runtime.staticCompositionLocalOf

val LocalCharacterItemNavigator = staticCompositionLocalOf<CharacterItemNavigator> {
    error("CharacterNavigator not provided")
}