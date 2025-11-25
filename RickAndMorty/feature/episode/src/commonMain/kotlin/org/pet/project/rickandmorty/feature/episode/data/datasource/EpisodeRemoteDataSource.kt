package org.pet.project.rickandmorty.feature.episode.data.datasource

import org.pet.project.rickandmorty.feature.episode.data.model.EpisodeResponse
import org.pet.project.rickandmorty.library.result.Result

internal interface EpisodeRemoteDataSource {

    suspend fun getEpisodes(ids: List<Int>): Result<List<EpisodeResponse>>
}