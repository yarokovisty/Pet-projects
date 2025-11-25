package org.pet.project.rickandmorty.feature.character.impl.data.datasource

import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.http.HttpMethod
import kotlinx.serialization.json.Json
import org.pet.project.rickandmorty.core.network.safeRequest
import org.pet.project.rickandmorty.library.result.Result
import org.pet.project.rickandmorty.feature.character.impl.data.model.CharacterListResponse
import org.pet.project.rickandmorty.feature.character.impl.data.model.CharacterResponse

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

    override suspend fun getCharacter(id: Int): Result<CharacterResponse> {
        return client.safeRequest<CharacterResponse>(json) {
            url("/api/character/$id")
            method = HttpMethod.Get
        }
    }
}