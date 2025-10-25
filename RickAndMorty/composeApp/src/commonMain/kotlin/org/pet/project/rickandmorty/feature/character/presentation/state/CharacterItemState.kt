package org.pet.project.rickandmorty.feature.character.presentation.state

import org.pet.project.rickandmorty.common.presentation.State
import org.pet.project.rickandmorty.feature.character.domain.entity.Gender
import org.pet.project.rickandmorty.feature.character.domain.entity.Status

internal data class CharacterItemState(
    val loading: Boolean = false,
    val error: Boolean = false,
    val character: CharacterContentState = CharacterContentState()
) : State

internal data class CharacterContentState(
    val name: String = "",
    val gender: Gender = Gender.UNKNOWN,
    val image: String = "",
    val status: Status = Status.UNKNOWN,
    val species: String = "",
    val origin: String = ""
)