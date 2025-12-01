package org.pet.project.rickandmorty.feature.episode.data.datasource

import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.http.HttpMethod
import kotlinx.serialization.json.Json
import org.pet.project.rickandmorty.core.network.safeRequest
import org.pet.project.rickandmorty.feature.episode.data.model.EpisodeListResponse
import org.pet.project.rickandmorty.feature.episode.data.model.EpisodeResponse
import org.pet.project.rickandmorty.library.result.Result

internal class EpisodeRemoteDataSourceImpl(
    private val client: HttpClient,
    private val json: Json
) : EpisodeRemoteDataSource {

    override suspend fun getEpisodes(ids: List<Int>): Result<List<EpisodeResponse>> {
        return client.safeRequest<List<EpisodeResponse>>(json) {
            url("/api/episode/${ids.joinToString(",")}")
            method = HttpMethod.Get
        }
    }

    override suspend fun getEpisodesByPage(page: Int): Result<EpisodeListResponse> {
        return client.safeRequest<EpisodeListResponse>(json) {
            url("/api/episode")
            parameter("page", page)
            method = HttpMethod.Get
        }
    }
}