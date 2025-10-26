package org.pet.project.rickandmorty.feature.character.presentation.event

import org.jetbrains.compose.resources.StringResource
import org.pet.project.rickandmorty.common.presentation.Event
import rickandmorty.composeapp.generated.resources.Res
import rickandmorty.composeapp.generated.resources.character_error_upload

sealed interface CharacterListEvent : Event {
    class Error(val errorMes: StringResource) : CharacterListEvent
    class OpenCharacterScreen(val id: Int) : CharacterListEvent
}