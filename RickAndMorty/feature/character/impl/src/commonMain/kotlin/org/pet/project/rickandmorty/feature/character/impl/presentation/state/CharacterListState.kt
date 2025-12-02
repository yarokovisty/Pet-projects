package org.pet.project.rickandmorty.feature.character.impl.presentation.state

import org.pet.project.rickandmorty.common.presentation.State
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Character

internal data class CharacterListState(
    val skeleton: Boolean = false,
    val uploading: Boolean = false,
    val error: Boolean = false,
    val uploadAllCharacters: Boolean = false,
    val characters: List<Character> = emptyList()
) : State