package org.pet.project.rickandmorty.feature.location.data.datasource

import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.http.HttpMethod
import kotlinx.serialization.json.Json
import org.pet.project.rickandmorty.core.networking.safeRequest
import org.pet.project.rickandmorty.library.result.Result
import org.pet.project.rickandmorty.feature.location.data.model.ApiLocationResponse
import org.pet.project.rickandmorty.feature.location.data.model.ResidentResponse

internal class RemoteLocationDataSourceImpl(
    private val client: HttpClient,
    private val json: Json
) : RemoteLocationDataSource {

    override suspend fun getLocationByName(name: String): Result<ApiLocationResponse> {
        return client.safeRequest<ApiLocationResponse>(json) {
            url("/api/location")
            parameter("name", name)
            method = HttpMethod.Get
        }
    }

    override suspend fun getResidents(id: Int): Result<ResidentResponse> {
        return client.safeRequest<ResidentResponse>(json) {
            url("/api/character/$id")
            method = HttpMethod.Get
        }
    }

}