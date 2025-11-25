package org.pet.project.rickandmorty.feature.character.impl.presentation.state

import org.pet.project.rickandmorty.common.presentation.State
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Character

internal data class CharacterItemState(
    val loading: Boolean = false,
    val error: Boolean = false,
    val character: Character? = null
) : State
