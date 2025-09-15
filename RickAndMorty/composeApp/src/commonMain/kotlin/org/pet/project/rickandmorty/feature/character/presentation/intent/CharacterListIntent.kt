package org.pet.project.rickandmorty.feature.character.presentation.intent

import org.pet.project.rickandmorty.common.presentation.Intent
import org.pet.project.rickandmorty.feature.character.domain.entity.Character

sealed interface CharacterListIntent : Intent {
    object UploadCharacterList : CharacterListIntent
    class OpenCharacterScreen(val character: Character) : CharacterListIntent
}