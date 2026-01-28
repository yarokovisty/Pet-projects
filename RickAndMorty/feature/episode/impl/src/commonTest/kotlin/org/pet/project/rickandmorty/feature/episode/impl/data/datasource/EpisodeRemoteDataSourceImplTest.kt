package org.pet.project.rickandmorty.feature.episode.impl.data.datasource

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
import org.pet.project.rickandmorty.feature.episode.impl.data.model.EpisodeListResponse
import org.pet.project.rickandmorty.feature.episode.impl.data.model.EpisodeResponse
import org.pet.project.rickandmorty.library.result.Result
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class EpisodeRemoteDataSourceImplTest {

    private lateinit var json: Json

    @BeforeTest
    fun setUp() {
        json = Json { ignoreUnknownKeys = true }
    }

    private fun createDataSource(
        responseBody: String,
        statusCode: HttpStatusCode = HttpStatusCode.OK
    ): EpisodeRemoteDataSourceImpl {
        val client = createMockClient(responseBody, statusCode)
        return EpisodeRemoteDataSourceImpl(client, json)
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

    // getEpisodesByPage tests

    @Test
    fun `getEpisodesByPage with valid response EXPECT Result Success`() = runTest {
        val dataSource = createDataSource(EpisodeTestJsonResources.episodeListSinglePage)

        val result = dataSource.getEpisodesByPage(page = 1)

        assertIs<Result.Success<*>>(result)
    }

    @Test
    fun `getEpisodesByPage with valid response EXPECT correct episode count`() = runTest {
        val dataSource = createDataSource(EpisodeTestJsonResources.episodeListTwoEpisodes)

        val result = dataSource.getEpisodesByPage(page = 1)

        val success = assertIs<Result.Success<EpisodeListResponse>>(result)
        assertEquals(2, success.value.results.size)
    }

    @Test
    fun `getEpisodesByPage with valid response EXPECT correct info pages`() = runTest {
        val dataSource = createDataSource(EpisodeTestJsonResources.episodeListTwoPages)

        val result = dataSource.getEpisodesByPage(page = 1)

        val success = assertIs<Result.Success<EpisodeListResponse>>(result)
        assertEquals(2, success.value.info.pages)
    }

    @Test
    fun `getEpisodesByPage with valid response EXPECT correct info count`() = runTest {
        val dataSource = createDataSource(EpisodeTestJsonResources.episodeListSinglePage)

        val result = dataSource.getEpisodesByPage(page = 1)

        val success = assertIs<Result.Success<EpisodeListResponse>>(result)
        assertEquals(51, success.value.info.count)
    }

    @Test
    fun `getEpisodesByPage with error response EXPECT Result Failure`() = runTest {
        val dataSource = createDataSource("", HttpStatusCode.InternalServerError)

        val result = dataSource.getEpisodesByPage(page = 1)

        assertIs<Result.Failure<*>>(result)
    }

    @Test
    fun `getEpisodesByPage with not found response EXPECT Result Failure`() = runTest {
        val dataSource = createDataSource("", HttpStatusCode.NotFound)

        val result = dataSource.getEpisodesByPage(page = 999)

        assertIs<Result.Failure<*>>(result)
    }

    @Test
    fun `getEpisodesByPage with empty results EXPECT empty list`() = runTest {
        val dataSource = createDataSource(EpisodeTestJsonResources.episodeListEmpty)

        val result = dataSource.getEpisodesByPage(page = 1)

        val success = assertIs<Result.Success<EpisodeListResponse>>(result)
        assertTrue(success.value.results.isEmpty())
    }

    @Test
    fun `getEpisodesByPage with valid response EXPECT correct first episode name`() = runTest {
        val dataSource = createDataSource(EpisodeTestJsonResources.episodeListSinglePage)

        val result = dataSource.getEpisodesByPage(page = 1)

        val success = assertIs<Result.Success<EpisodeListResponse>>(result)
        assertEquals("Pilot", success.value.results.first().name)
    }

    @Test
    fun `getEpisodesByPage with valid response EXPECT correct first episode id`() = runTest {
        val dataSource = createDataSource(EpisodeTestJsonResources.episodeListSinglePage)

        val result = dataSource.getEpisodesByPage(page = 1)

        val success = assertIs<Result.Success<EpisodeListResponse>>(result)
        assertEquals(1, success.value.results.first().id)
    }

    @Test
    fun `getEpisodesByPage with valid response EXPECT correct episode code`() = runTest {
        val dataSource = createDataSource(EpisodeTestJsonResources.episodeListSinglePage)

        val result = dataSource.getEpisodesByPage(page = 1)

        val success = assertIs<Result.Success<EpisodeListResponse>>(result)
        assertEquals("S01E01", success.value.results.first().episode)
    }

    @Test
    fun `getEpisodesByPage with valid response EXPECT correct air date`() = runTest {
        val dataSource = createDataSource(EpisodeTestJsonResources.episodeListSinglePage)

        val result = dataSource.getEpisodesByPage(page = 1)

        val success = assertIs<Result.Success<EpisodeListResponse>>(result)
        assertEquals("December 2, 2013", success.value.results.first().airDate)
    }

    // getEpisodes tests

    @Test
    fun `getEpisodes with valid ids EXPECT Result Success`() = runTest {
        val dataSource = createDataSource(EpisodeTestJsonResources.multipleEpisodes)

        val result = dataSource.getEpisodes(ids = listOf(1, 2, 3))

        assertIs<Result.Success<*>>(result)
    }

    @Test
    fun `getEpisodes with valid ids EXPECT correct episode count`() = runTest {
        val dataSource = createDataSource(EpisodeTestJsonResources.multipleEpisodes)

        val result = dataSource.getEpisodes(ids = listOf(1, 2, 3))

        val success = assertIs<Result.Success<List<EpisodeResponse>>>(result)
        assertEquals(3, success.value.size)
    }

    @Test
    fun `getEpisodes with valid ids EXPECT correct first episode name`() = runTest {
        val dataSource = createDataSource(EpisodeTestJsonResources.multipleEpisodes)

        val result = dataSource.getEpisodes(ids = listOf(1, 2, 3))

        val success = assertIs<Result.Success<List<EpisodeResponse>>>(result)
        assertEquals("Pilot", success.value.first().name)
    }

    @Test
    fun `getEpisodes with valid ids EXPECT correct first episode id`() = runTest {
        val dataSource = createDataSource(EpisodeTestJsonResources.multipleEpisodes)

        val result = dataSource.getEpisodes(ids = listOf(1, 2, 3))

        val success = assertIs<Result.Success<List<EpisodeResponse>>>(result)
        assertEquals(1, success.value.first().id)
    }

    @Test
    fun `getEpisodes with valid ids EXPECT correct second episode name`() = runTest {
        val dataSource = createDataSource(EpisodeTestJsonResources.multipleEpisodes)

        val result = dataSource.getEpisodes(ids = listOf(1, 2, 3))

        val success = assertIs<Result.Success<List<EpisodeResponse>>>(result)
        assertEquals("Lawnmower Dog", success.value[1].name)
    }

    @Test
    fun `getEpisodes with valid ids EXPECT correct episode code`() = runTest {
        val dataSource = createDataSource(EpisodeTestJsonResources.multipleEpisodes)

        val result = dataSource.getEpisodes(ids = listOf(1, 2, 3))

        val success = assertIs<Result.Success<List<EpisodeResponse>>>(result)
        assertEquals("S01E01", success.value.first().episode)
    }

    @Test
    fun `getEpisodes with valid ids EXPECT correct air date`() = runTest {
        val dataSource = createDataSource(EpisodeTestJsonResources.multipleEpisodes)

        val result = dataSource.getEpisodes(ids = listOf(1, 2, 3))

        val success = assertIs<Result.Success<List<EpisodeResponse>>>(result)
        assertEquals("December 2, 2013", success.value.first().airDate)
    }

    @Test
    fun `getEpisodes with error response EXPECT Result Failure`() = runTest {
        val dataSource = createDataSource("", HttpStatusCode.InternalServerError)

        val result = dataSource.getEpisodes(ids = listOf(1, 2, 3))

        assertIs<Result.Failure<*>>(result)
    }

    @Test
    fun `getEpisodes with not found response EXPECT Result Failure`() = runTest {
        val dataSource = createDataSource("", HttpStatusCode.NotFound)

        val result = dataSource.getEpisodes(ids = listOf(99999))

        assertIs<Result.Failure<*>>(result)
    }
}
