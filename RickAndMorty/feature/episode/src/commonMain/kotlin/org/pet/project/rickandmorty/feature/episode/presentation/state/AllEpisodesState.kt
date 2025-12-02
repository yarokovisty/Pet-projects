package org.pet.project.rickandmorty.feature.episode.presentation.state

import org.pet.project.rickandmorty.common.presentation.State
import org.pet.project.rickandmorty.feature.episode.domain.entity.Episode

internal data class AllEpisodesState(
    val loading: Boolean = false,
    val error: Boolean = false,
    val uploading: Boolean = false,
    val end: Boolean = false,
    val episodes: Map<Int, List<Episode>> = emptyMap(),
    val lastEpisodeIndex: Int? = null
) : State