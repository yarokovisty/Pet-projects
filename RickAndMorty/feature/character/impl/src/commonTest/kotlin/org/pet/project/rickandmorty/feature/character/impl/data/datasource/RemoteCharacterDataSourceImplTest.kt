package org.pet.project.rickandmorty.feature.character.impl.data.datasource

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.pet.project.rickandmorty.feature.character.impl.data.model.CharacterListResponse
import org.pet.project.rickandmorty.feature.character.impl.data.model.CharacterResponse
import org.pet.project.rickandmorty.library.result.Result
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class RemoteCharacterDataSourceImplTest {

    private lateinit var json: Json

    @BeforeTest
    fun setUp() {
        json = Json { ignoreUnknownKeys = true }
    }

    private fun createDataSource(
        responseBody: String,
        statusCode: HttpStatusCode = HttpStatusCode.OK
    ): RemoteCharacterDataSourceImpl {
        val client = createMockClient(responseBody, statusCode)
        return RemoteCharacterDataSourceImpl(client, json)
    }

    @Test
    fun `getCharactersPage with valid response EXPECT Result Success`() = runTest {
        val dataSource = createDataSource(TestJsonResources.characterListSingleCharacterOnePage)

        val result = dataSource.getCharactersPage(pageNumber = 1)

        assertIs<Result.Success<*>>(result)
    }

    @Test
    fun `getCharactersPage with valid response EXPECT correct character count`() = runTest {
        val dataSource = createDataSource(TestJsonResources.characterListTwoCharactersOnePage)

        val result = dataSource.getCharactersPage(pageNumber = 1)

        val success = assertIs<Result.Success<CharacterListResponse>>(result)
        assertEquals(2, success.value.results.size)
    }

    @Test
    fun `getCharactersPage with valid response EXPECT correct info pages`() = runTest {
        val dataSource = createDataSource(TestJsonResources.characterListTwoPages)

        val result = dataSource.getCharactersPage(pageNumber = 1)

        val success = assertIs<Result.Success<CharacterListResponse>>(result)
        assertEquals(2, success.value.info.pages)
    }

    @Test
    fun `getCharactersPage with error response EXPECT Result Failure`() = runTest {
        val dataSource = createDataSource("", HttpStatusCode.InternalServerError)

        val result = dataSource.getCharactersPage(pageNumber = 1)

        assertIs<Result.Failure<*>>(result)
    }

    @Test
    fun `getCharactersPage with not found response EXPECT Result Failure`() = runTest {
        val dataSource = createDataSource("", HttpStatusCode.NotFound)

        val result = dataSource.getCharactersPage(pageNumber = 999)

        assertIs<Result.Failure<*>>(result)
    }

    @Test
    fun `getCharacter with valid id EXPECT Result Success`() = runTest {
        val dataSource = createDataSource(TestJsonResources.characterRickSanchez)

        val result = dataSource.getCharacter(id = 1)

        assertIs<Result.Success<*>>(result)
    }

    @Test
    fun `getCharacter with valid id EXPECT correct character id`() = runTest {
        val dataSource = createDataSource(TestJsonResources.characterRickId42)

        val result = dataSource.getCharacter(id = 42)

        val success = assertIs<Result.Success<CharacterResponse>>(result)
        assertEquals(42, success.value.id)
    }

    @Test
    fun `getCharacter with valid id EXPECT correct character name`() = runTest {
        val dataSource = createDataSource(TestJsonResources.characterMortySmith)

        val result = dataSource.getCharacter(id = 1)

        val success = assertIs<Result.Success<CharacterResponse>>(result)
        assertEquals("Morty Smith", success.value.name)
    }

    @Test
    fun `getCharacter with not found id EXPECT Result Failure`() = runTest {
        val dataSource = createDataSource("", HttpStatusCode.NotFound)

        val result = dataSource.getCharacter(id = 99999)

        assertIs<Result.Failure<*>>(result)
    }

    @Test
    fun `getAllCharactersByName with single page EXPECT Result Success`() = runTest {
        val dataSource = createDataSource(TestJsonResources.characterListSinglePageNoNext)

        val result = dataSource.getAllCharactersByName(name = "Rick")

        assertIs<Result.Success<*>>(result)
    }

    @Test
    fun `getAllCharactersByName with single page EXPECT correct character count`() = runTest {
        val dataSource = createDataSource(TestJsonResources.characterListTwoCharactersNoNext)

        val result = dataSource.getAllCharactersByName(name = "Rick")

        val success = assertIs<Result.Success<List<CharacterResponse>>>(result)
        assertEquals(2, success.value.size)
    }

    @Test
    fun `getAllCharactersByName with no results EXPECT empty list`() = runTest {
        val dataSource = createDataSource(TestJsonResources.characterListEmpty)

        val result = dataSource.getAllCharactersByName(name = "NonExistent")

        val success = assertIs<Result.Success<List<CharacterResponse>>>(result)
        assertTrue(success.value.isEmpty())
    }

    @Test
    fun `getAllCharactersByName with error EXPECT Result Failure`() = runTest {
        val dataSource = createDataSource("", HttpStatusCode.InternalServerError)

        val result = dataSource.getAllCharactersByName(name = "Rick")

        assertIs<Result.Failure<*>>(result)
    }

    @Test
    fun `getCharactersPage with params EXPECT Result Success`() = runTest {
        val dataSource = createDataSource(TestJsonResources.characterListSingleCharacterOnePage)

        val result = dataSource.getCharactersPage(pageNumber = 1, params = mapOf("name" to "Rick"))

        assertIs<Result.Success<*>>(result)
    }

    @Test
    fun `getCharactersPage with empty results EXPECT empty list`() = runTest {
        val dataSource = createDataSource(TestJsonResources.characterListEmpty)

        val result = dataSource.getCharactersPage(pageNumber = 1)

        val success = assertIs<Result.Success<CharacterListResponse>>(result)
        assertTrue(success.value.results.isEmpty())
    }

    private fun createMockClient(
        responseBody: String,
        statusCode: HttpStatusCode = HttpStatusCode.OK
    ): HttpClient {
        val mockEngine = MockEngine { _ ->
            respond(
                content = responseBody,
                status = statusCode,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }
        return HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(json)
            }
        }
    }
}
