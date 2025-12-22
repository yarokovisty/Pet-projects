package org.pet.project.rickandmorty.feature.episode.impl.presentation.state

import org.pet.project.rickandmorty.common.presentation.State
import org.pet.project.rickandmorty.feature.episode.api.domain.entity.Season

internal data class CharacterEpisodeState(
    val loading: Boolean = false,
    val error: Boolean = false,
    val seasons: List<Season> = emptyList(),
    val character: CharacterState = CharacterState()
) : State

internal data class CharacterState(
    val name: String = "",
    val image: String = ""
)

