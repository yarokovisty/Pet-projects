package org.pet.project.rickandmorty.feature.character.impl.navigation

interface CharacterNavigator{

    fun openCharacterItemScreen(characterId: Int)

    fun openLocationScreen(locationName: String)

    fun openCharacterEpisodeScreen(characterId: Int)

    fun back()
}