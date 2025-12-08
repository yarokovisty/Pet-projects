package org.pet.project.rickandmorty.feature.character.impl.data.datasource

import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.http.HttpMethod
import kotlinx.serialization.json.Json
import org.pet.project.rickandmorty.core.network.safeRequest
import org.pet.project.rickandmorty.feature.character.impl.data.model.CharacterListResponse
import org.pet.project.rickandmorty.feature.character.impl.data.model.CharacterResponse
import org.pet.project.rickandmorty.library.result.Result

internal class RemoteCharacterDataSourceImpl(
    private val client: HttpClient,
    private val json: Json
) : RemoteCharacterDataSource {

    private companion object {
        const val URL = "/api/character"
    }

    override suspend fun getCharactersPage(page: Int): Result<CharacterListResponse> {
        return client.safeRequest<CharacterListResponse>(json) {
            url(URL)
            parameter("page", page)
            method = HttpMethod.Get
        }
    }

    override suspend fun getCharacter(id: Int): Result<CharacterResponse> {
        return client.safeRequest<CharacterResponse>(json) {
            url("$URL/$id")
            method = HttpMethod.Get
        }
    }

    override suspend fun getCharactersByName(name: String): Result<CharacterListResponse> {
        return client.safeRequest<CharacterListResponse>(json) {
            url(URL)
            parameter("name", name)
            method = HttpMethod.Get
        }
    }
}