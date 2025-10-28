package org.pet.project.rickandmorty.feature.character.navigation

import androidx.navigation.NavHostController

interface CharacterNavigator {
    val navController: NavHostController

    fun openCharacterItemScreen(characterId: Int)

    fun openLocationScreen(locationName: String)

    fun back()
}