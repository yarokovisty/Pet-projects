package org.pet.project.rickandmorty.feature.episode.impl.data.datasource

import org.pet.project.rickandmorty.feature.episode.impl.data.model.EpisodeListResponse
import org.pet.project.rickandmorty.feature.episode.impl.data.model.EpisodeResponse
import org.pet.project.rickandmorty.library.result.Result

internal class FakeEpisodeRemoteDataSource : EpisodeRemoteDataSource {

    private val pageResponses = mutableMapOf<Int, Result<EpisodeListResponse>>()
    private var defaultPageResponse: Result<EpisodeListResponse>? = null
    private var episodesResponse: Result<List<EpisodeResponse>> =
        Result.Failure.Error(IllegalStateException("No response configured"))

    var getEpisodesByPageCallCount = 0
        private set

    var lastRequestedPage: Int? = null
        private set

    var getEpisodesCallCount = 0
        private set

    var lastRequestedIds: List<Int>? = null
        private set

    fun setPageResponse(page: Int, response: Result<EpisodeListResponse>) {
        pageResponses[page] = response
    }

    fun setDefaultPageResponse(response: Result<EpisodeListResponse>) {
        defaultPageResponse = response
    }

    fun setEpisodesResponse(response: Result<List<EpisodeResponse>>) {
        episodesResponse = response
    }

    override suspend fun getEpisodesByPage(page: Int): Result<EpisodeListResponse> {
        getEpisodesByPageCallCount++
        lastRequestedPage = page
        return pageResponses[page]
            ?: defaultPageResponse
            ?: Result.Failure.Error(IllegalStateException("No response configured for page $page"))
    }

    override suspend fun getEpisodes(ids: List<Int>): Result<List<EpisodeResponse>> {
        getEpisodesCallCount++
        lastRequestedIds = ids
        return episodesResponse
    }
}
