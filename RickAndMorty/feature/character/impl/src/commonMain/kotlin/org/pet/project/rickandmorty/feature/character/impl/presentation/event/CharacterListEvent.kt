package org.pet.project.rickandmorty.feature.character.impl.presentation.event

import org.jetbrains.compose.resources.StringResource
import org.pet.project.rickandmorty.common.presentation.Event

sealed interface CharacterListEvent : Event {
    class Error(val errorMes: StringResource) : CharacterListEvent
    class OpenCharacterScreen(val id: Int) : CharacterListEvent
}