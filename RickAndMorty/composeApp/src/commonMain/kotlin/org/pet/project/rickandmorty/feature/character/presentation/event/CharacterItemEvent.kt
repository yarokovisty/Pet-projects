package org.pet.project.rickandmorty.feature.character.presentation.event

import org.pet.project.rickandmorty.common.presentation.Event

sealed interface CharacterItemEvent : Event {
    class OpenLocationScreen(val name: String) : CharacterItemEvent
}