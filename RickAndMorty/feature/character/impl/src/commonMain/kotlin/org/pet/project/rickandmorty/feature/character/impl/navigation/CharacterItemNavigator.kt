package org.pet.project.rickandmorty.feature.character.impl.navigation

interface CharacterItemNavigator{

    fun openLocationScreen(locationName: String)

    fun openCharacterEpisodeScreen(characterId: Int)

    fun back()
}