package org.pet.project.rickandmorty.feature.character.data.datasource

import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.http.HttpMethod
import kotlinx.serialization.json.Json
import org.pet.project.rickandmorty.common.utils.Result
import org.pet.project.rickandmorty.core.networking.safeRequest
import org.pet.project.rickandmorty.feature.character.data.model.CharacterListResponse

internal class RemoteCharacterDataSourceImpl(
    private val client: HttpClient,
    private val json: Json
) : RemoteCharacterDataSource {

    override suspend fun getCharactersPage(page: Int): Result<CharacterListResponse> {
        return client.safeRequest<CharacterListResponse>(json) {
            url("/api/character")
            parameter("page", page)
            method = HttpMethod.Get
        }
    }
}