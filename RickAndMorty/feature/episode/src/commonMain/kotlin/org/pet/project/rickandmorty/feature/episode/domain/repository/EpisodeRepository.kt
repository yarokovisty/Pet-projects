package org.pet.project.rickandmorty.feature.episode.domain.repository

import org.pet.project.rickandmorty.feature.episode.domain.entity.Season
import org.pet.project.rickandmorty.library.result.Result

interface EpisodeRepository {

    suspend fun getEpisodes(ids: List<Int>): Result<List<Season>>
}