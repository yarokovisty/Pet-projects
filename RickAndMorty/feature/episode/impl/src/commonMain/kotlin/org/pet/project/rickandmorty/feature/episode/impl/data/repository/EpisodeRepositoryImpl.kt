package org.pet.project.rickandmorty.feature.episode.impl.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.pet.project.rickandmorty.feature.episode.api.domain.entity.EpisodeState
import org.pet.project.rickandmorty.feature.episode.api.domain.entity.Season
import org.pet.project.rickandmorty.feature.episode.api.domain.repository.EpisodeRepository
import org.pet.project.rickandmorty.feature.episode.impl.data.datasource.EpisodeRemoteDataSource
import org.pet.project.rickandmorty.feature.episode.impl.data.mapper.toItem
import org.pet.project.rickandmorty.feature.episode.impl.data.mapper.toSeasons
import org.pet.project.rickandmorty.feature.episode.impl.data.paginator.EpisodePaginator
import org.pet.project.rickandmorty.library.result.Result
import org.pet.project.rickandmorty.library.result.map

internal class EpisodeRepositoryImpl(
    val remoteDataSource: EpisodeRemoteDataSource,
    val paginator: EpisodePaginator
) : EpisodeRepository {

    override val episodes: Flow<EpisodeState> = paginator.paginationFlow.map { it.toItem() }

    override suspend fun getEpisodes(ids: List<Int>): Result<List<Season>> {
        return remoteDataSource.getEpisodes(ids).map { episodes ->
            episodes.toSeasons()
        }
    }

    override suspend fun loadEpisodes() = paginator.loadItems()
}