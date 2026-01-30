package org.pet.project.rickandmorty.feature.location.impl.data.datasource

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
import org.pet.project.rickandmorty.feature.location.impl.data.model.ApiLocationResponse
import org.pet.project.rickandmorty.feature.location.impl.data.model.ResidentResponse
import org.pet.project.rickandmorty.library.result.Result
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class RemoteLocationDataSourceImplTest {

    private lateinit var json: Json

    @BeforeTest
    fun setUp() {
        json = Json { ignoreUnknownKeys = true }
    }

    private fun createDataSource(
        responseBody: String,
        statusCode: HttpStatusCode = HttpStatusCode.OK
    ): RemoteLocationDataSourceImpl {
        val client = createMockClient(responseBody, statusCode)
        return RemoteLocationDataSourceImpl(client, json)
    }

    @Test
    fun `getLocationByName with valid response EXPECT Result Success`() = runTest {
        val dataSource = createDataSource(TestJsonResources.locationListSingleLocation)

        val result = dataSource.getLocationByName(name = "Earth")

        assertIs<Result.Success<*>>(result)
    }

    @Test
    fun `getLocationByName with valid response EXPECT correct location count`() = runTest {
        val dataSource = createDataSource(TestJsonResources.locationListTwoLocations)

        val result = dataSource.getLocationByName(name = "Earth")

        val success = assertIs<Result.Success<ApiLocationResponse>>(result)
        assertEquals(2, success.value.results.size)
    }

    @Test
    fun `getLocationByName with valid response EXPECT correct location name`() = runTest {
        val dataSource = createDataSource(TestJsonResources.locationEarthC137)

        val result = dataSource.getLocationByName(name = "Earth")

        val success = assertIs<Result.Success<ApiLocationResponse>>(result)
        assertEquals("Earth (C-137)", success.value.results.first().name)
    }

    @Test
    fun `getLocationByName with valid response EXPECT correct location id`() = runTest {
        val dataSource = createDataSource(TestJsonResources.locationEarthC137)

        val result = dataSource.getLocationByName(name = "Earth")

        val success = assertIs<Result.Success<ApiLocationResponse>>(result)
        assertEquals(1, success.value.results.first().id)
    }

    @Test
    fun `getLocationByName with valid response EXPECT correct location type`() = runTest {
        val dataSource = createDataSource(TestJsonResources.locationEarthC137)

        val result = dataSource.getLocationByName(name = "Earth")

        val success = assertIs<Result.Success<ApiLocationResponse>>(result)
        assertEquals("Planet", success.value.results.first().type)
    }

    @Test
    fun `getLocationByName with valid response EXPECT correct dimension`() = runTest {
        val dataSource = createDataSource(TestJsonResources.locationEarthC137)

        val result = dataSource.getLocationByName(name = "Earth")

        val success = assertIs<Result.Success<ApiLocationResponse>>(result)
        assertEquals("Dimension C-137", success.value.results.first().dimension)
    }

    @Test
    fun `getLocationByName with valid response EXPECT correct residents count`() = runTest {
        val dataSource = createDataSource(TestJsonResources.locationEarthC137)

        val result = dataSource.getLocationByName(name = "Earth")

        val success = assertIs<Result.Success<ApiLocationResponse>>(result)
        assertEquals(2, success.value.results.first().residents.size)
    }

    @Test
    fun `getLocationByName with empty results EXPECT empty list`() = runTest {
        val dataSource = createDataSource(TestJsonResources.locationListEmpty)

        val result = dataSource.getLocationByName(name = "NonExistent")

        val success = assertIs<Result.Success<ApiLocationResponse>>(result)
        assertTrue(success.value.results.isEmpty())
    }

    @Test
    fun `getLocationByName with no residents EXPECT empty residents list`() = runTest {
        val dataSource = createDataSource(TestJsonResources.locationCitadelOfRicks)

        val result = dataSource.getLocationByName(name = "Citadel")

        val success = assertIs<Result.Success<ApiLocationResponse>>(result)
        assertTrue(success.value.results.first().residents.isEmpty())
    }

    @Test
    fun `getLocationByName with error response EXPECT Result Failure`() = runTest {
        val dataSource = createDataSource("", HttpStatusCode.InternalServerError)

        val result = dataSource.getLocationByName(name = "Earth")

        assertIs<Result.Failure<*>>(result)
    }

    @Test
    fun `getLocationByName with not found response EXPECT Result Failure`() = runTest {
        val dataSource = createDataSource("", HttpStatusCode.NotFound)

        val result = dataSource.getLocationByName(name = "NonExistent")

        assertIs<Result.Failure<*>>(result)
    }

    @Test
    fun `getResidents with valid id EXPECT Result Success`() = runTest {
        val dataSource = createDataSource(TestJsonResources.residentBethSmith)

        val result = dataSource.getResidents(id = 38)

        assertIs<Result.Success<*>>(result)
    }

    @Test
    fun `getResidents with valid id EXPECT correct resident id`() = runTest {
        val dataSource = createDataSource(TestJsonResources.residentId42)

        val result = dataSource.getResidents(id = 42)

        val success = assertIs<Result.Success<ResidentResponse>>(result)
        assertEquals(42, success.value.id)
    }

    @Test
    fun `getResidents with valid id EXPECT correct resident name`() = runTest {
        val dataSource = createDataSource(TestJsonResources.residentRickSanchez)

        val result = dataSource.getResidents(id = 1)

        val success = assertIs<Result.Success<ResidentResponse>>(result)
        assertEquals("Rick Sanchez", success.value.name)
    }

    @Test
    fun `getResidents with valid id EXPECT correct image url`() = runTest {
        val dataSource = createDataSource(TestJsonResources.residentBethSmith)

        val result = dataSource.getResidents(id = 38)

        val success = assertIs<Result.Success<ResidentResponse>>(result)
        assertEquals("https://rickandmortyapi.com/api/character/avatar/38.jpeg", success.value.image)
    }

    @Test
    fun `getResidents with not found id EXPECT Result Failure`() = runTest {
        val dataSource = createDataSource("", HttpStatusCode.NotFound)

        val result = dataSource.getResidents(id = 99999)

        assertIs<Result.Failure<*>>(result)
    }

    @Test
    fun `getResidents with error response EXPECT Result Failure`() = runTest {
        val dataSource = createDataSource("", HttpStatusCode.InternalServerError)

        val result = dataSource.getResidents(id = 1)

        assertIs<Result.Failure<*>>(result)
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
