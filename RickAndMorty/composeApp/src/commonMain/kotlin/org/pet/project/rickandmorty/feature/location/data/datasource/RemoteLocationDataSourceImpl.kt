package org.pet.project.rickandmorty.feature.location.data.datasource

import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.http.HttpMethod
import kotlinx.serialization.json.Json
import org.pet.project.rickandmorty.core.networking.safeRequest
import org.pet.project.rickandmorty.core.result.Result
import org.pet.project.rickandmorty.feature.location.data.model.LocationResponse

internal class RemoteLocationDataSourceImpl(
    private val client: HttpClient,
    private val json: Json
) : RemoteLocationDataSource {

    override suspend fun getLocationByName(name: String): Result<LocationResponse> {
        return client.safeRequest<LocationResponse>(json) {
            url("/api/location")
            parameter("name", name)
            method = HttpMethod.Get
        }
    }
}