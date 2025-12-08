package org.pet.project.rickandmorty.feature.character.impl.presentation.event

import org.pet.project.rickandmorty.common.presentation.Event

sealed interface CharacterSearchEvent : Event {
    class OpenCharacterScreen(val characterId: Int) : CharacterSearchEvent
}