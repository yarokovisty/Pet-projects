package org.pet.project.rickandmorty.feature.episode.api.domain.repository

import kotlinx.coroutines.flow.Flow
import org.pet.project.rickandmorty.feature.episode.api.domain.entity.EpisodeState
import org.pet.project.rickandmorty.feature.episode.api.domain.entity.Season
import org.pet.project.rickandmorty.library.result.Result

interface EpisodeRepository {

    val episodes: Flow<EpisodeState>

    suspend fun getEpisodes(ids: List<Int>): Result<List<Season>>

    suspend fun loadEpisodes()
}