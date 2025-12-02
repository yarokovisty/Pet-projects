package org.pet.project.rickandmorty.feature.character.impl.navigation

import androidx.compose.runtime.staticCompositionLocalOf

interface CharacterItemNavigator{

    fun openLocationScreen(locationName: String)

    fun openCharacterEpisodeScreen(characterId: Int)

    fun back()
}

val LocalCharacterItemNavigator = staticCompositionLocalOf<CharacterItemNavigator> {
    error("CharacterNavigator not provided")
}