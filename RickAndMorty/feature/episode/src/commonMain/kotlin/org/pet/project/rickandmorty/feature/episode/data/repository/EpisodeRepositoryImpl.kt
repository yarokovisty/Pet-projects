package org.pet.project.rickandmorty.feature.episode.data.repository

import org.pet.project.rickandmorty.feature.episode.data.datasource.EpisodeRemoteDataSource
import org.pet.project.rickandmorty.feature.episode.data.mapper.toSeasons
import org.pet.project.rickandmorty.feature.episode.domain.entity.Season
import org.pet.project.rickandmorty.feature.episode.domain.repository.EpisodeRepository
import org.pet.project.rickandmorty.library.result.Result
import org.pet.project.rickandmorty.library.result.map

internal class EpisodeRepositoryImpl(
    val remoteDataSource: EpisodeRemoteDataSource
) : EpisodeRepository {

    override suspend fun getEpisodes(ids: List<Int>): Result<List<Season>> {
        return remoteDataSource.getEpisodes(ids).map { episodes ->
            episodes.toSeasons()
        }
    }
}