package org.pet.project.rickandmorty.feature.character.presentation.event

import org.jetbrains.compose.resources.StringResource
import org.pet.project.rickandmorty.common.presentation.Event
import rickandmorty.composeapp.generated.resources.Res
import rickandmorty.composeapp.generated.resources.character_error_upload

enum class CharacterListEvent(val errorMessage: StringResource) : Event {
    ErrorUploadCharacters(Res.string.character_error_upload)
}