package org.pet.project.rickandmorty.feature.character.presentation.intent

import org.pet.project.rickandmorty.common.presentation.Intent

sealed interface CharacterItemIntent : Intent {
    object Refresh : CharacterItemIntent
    object Back : CharacterItemIntent
    class OpenOriginScreen(val name: String) : CharacterItemIntent
    class OpenLocationScreen(val name: String) : CharacterItemIntent
    class OpenAllEpisodes(val characterId: Int) : CharacterItemIntent
}