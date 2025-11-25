package org.pet.project.rickandmorty.feature.character.impl.presentation.intent

import org.pet.project.rickandmorty.common.presentation.Intent

sealed interface CharacterItemIntent : Intent {
    object Refresh : CharacterItemIntent
    object Back : CharacterItemIntent
    class OpenOriginScreen(val name: String) : CharacterItemIntent
    class OpenLocationScreen(val name: String) : CharacterItemIntent
    object OpenAllEpisodes : CharacterItemIntent
}