package org.pet.project.rickandmorty.feature.character.presentation.event

import org.pet.project.rickandmorty.common.presentation.Event

sealed interface CharacterListEvent : Event {
    object ErrorUploadCharacters : CharacterListEvent
}