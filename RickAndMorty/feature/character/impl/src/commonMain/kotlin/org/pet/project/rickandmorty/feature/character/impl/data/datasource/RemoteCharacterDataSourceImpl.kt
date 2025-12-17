package org.pet.project.rickandmorty.feature.character.impl.data.datasource

import io.ktor.client.HttpClient
import io.ktor.client.request.url
import io.ktor.http.HttpMethod
import io.ktor.util.appendAll
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.json.Json
import org.pet.project.rickandmorty.core.network.safeRequest
import org.pet.project.rickandmorty.feature.character.impl.data.model.CharacterListResponse
import org.pet.project.rickandmorty.feature.character.impl.data.model.CharacterResponse
import org.pet.project.rickandmorty.library.result.Result
import org.pet.project.rickandmorty.library.result.asSuccess
import org.pet.project.rickandmorty.library.result.isFailure
import org.pet.project.rickandmorty.library.result.isSuccess

internal class RemoteCharacterDataSourceImpl(
    private val client: HttpClient,
    private val json: Json,
) : RemoteCharacterDataSource {

    private companion object {

        const val URL = "/api/character"
        const val PAGE_PARAM_KEY = "page"
        const val NAME_PARAM_KEY = "name"
        const val INITIAL_PAGE = 1
    }

    override suspend fun getCharactersPage(
        pageNumber: Int,
        params: Map<String, String>,
    ): Result<CharacterListResponse> {
        return client.safeRequest<CharacterListResponse>(json) {
            url(URL)
            url {
                parameters.append(PAGE_PARAM_KEY, pageNumber.toString())
                parameters.appendAll(params)
            }

            method = HttpMethod.Get
        }
    }

    override suspend fun getCharacter(id: Int): Result<CharacterResponse> {
        return client.safeRequest<CharacterResponse>(json) {
            url("$URL/$id")
            method = HttpMethod.Get
        }
    }

    override suspend fun getAllCharactersByName(
        name: String
    ): Result<List<CharacterResponse>> = coroutineScope {
        val params = mapOf(NAME_PARAM_KEY to name)

        val firstPageResult = getCharactersPage(pageNumber = INITIAL_PAGE, params = params)

        if (firstPageResult.isFailure()) return@coroutineScope firstPageResult

        val firstPage = firstPageResult.asSuccess().value.results
        val totalPages = firstPageResult.asSuccess().value.info.pages

        val nextPage = INITIAL_PAGE + 1
        val pagesDeferred = (nextPage..totalPages).map { page ->
            async { getCharactersPage(page, params) }
        }
        val pages = pagesDeferred
            .awaitAll()
            .filter { it.isSuccess() }
            .flatMap { it.asSuccess().value.results }

        Result.Success.Value(firstPage + pages)
    }
}