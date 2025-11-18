package org.pet.project.rickandmorty.feature.character.navigation

interface CharacterNavigator{

    fun openCharacterItemScreen(characterId: Int)

    fun openLocationScreen(locationName: String)

    fun back()
}