package org.pet.project.rickandmorty.feature.character.impl.presentation.intent

import org.pet.project.rickandmorty.common.presentation.Intent

internal sealed interface CharacterListIntent : Intent {
    object Refresh : CharacterListIntent
    object Upload : CharacterListIntent
    class OpenCharacterScreen(val id: Int) : CharacterListIntent
}