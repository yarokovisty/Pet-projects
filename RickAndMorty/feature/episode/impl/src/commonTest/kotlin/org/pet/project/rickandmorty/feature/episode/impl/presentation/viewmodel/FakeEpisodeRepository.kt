package org.pet.project.rickandmorty.feature.episode.impl.presentation.viewmodel

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import org.pet.project.rickandmorty.feature.episode.api.domain.entity.Episode
import org.pet.project.rickandmorty.feature.episode.api.domain.entity.EpisodeState
import org.pet.project.rickandmorty.feature.episode.api.domain.entity.Season
import org.pet.project.rickandmorty.feature.episode.api.domain.repository.EpisodeRepository
import org.pet.project.rickandmorty.library.result.Result

internal class FakeEpisodeRepository : EpisodeRepository {

    private val _episodes = MutableSharedFlow<EpisodeState>(replay = 1)
    override val episodes: Flow<EpisodeState> = _episodes

    var loadEpisodesCallCount = 0
        private set

    var getEpisodesCallCount = 0
        private set

    var getEpisodesResult: Result<List<Season>> = Result.Success.Value(emptyList())

    suspend fun emitEpisodeState(state: EpisodeState) {
        _episodes.emit(state)
    }

    override suspend fun loadEpisodes() {
        loadEpisodesCallCount++
    }

    override suspend fun getEpisodes(ids: List<Int>): Result<List<Season>> {
        getEpisodesCallCount++
        return getEpisodesResult
    }

    companion object {
        fun createTestEpisode(
            id: Int = 1,
            name: String = "Pilot",
            season: Int = 1,
            seria: Int = 1,
            airDate: String = "December 2, 2013"
        ) = Episode(
            id = id,
            name = name,
            season = season,
            seria = seria,
            airDate = airDate
        )

        fun createTestEpisodesMap(seasonCount: Int = 1, episodesPerSeason: Int = 2): Map<Int, List<Episode>> {
            return (1..seasonCount).associate { season ->
                season to (1..episodesPerSeason).map { ep ->
                    createTestEpisode(
                        id = (season - 1) * episodesPerSeason + ep,
                        season = season,
                        seria = ep
                    )
                }
            }
        }
    }
}
