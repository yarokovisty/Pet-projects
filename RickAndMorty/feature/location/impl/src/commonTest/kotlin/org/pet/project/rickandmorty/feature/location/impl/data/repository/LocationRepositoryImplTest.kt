package org.pet.project.rickandmorty.feature.location.impl.data.repository

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.pet.project.rickandmorty.feature.location.api.domain.entity.ResidentState
import org.pet.project.rickandmorty.feature.location.impl.data.datasource.RemoteLocationDataSource
import org.pet.project.rickandmorty.feature.location.impl.data.model.ApiLocationResponse
import org.pet.project.rickandmorty.feature.location.impl.data.model.LocationResponse
import org.pet.project.rickandmorty.feature.location.impl.data.model.ResidentResponse
import org.pet.project.rickandmorty.feature.location.impl.data.paginator.ResidentsPaginator
import org.pet.project.rickandmorty.library.result.Result
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class LocationRepositoryImplTest {

    private fun createLocationResponse(
        id: Int = 1,
        name: String = "Earth (C-137)",
        type: String = "Planet",
        dimension: String = "Dimension C-137",
        residents: List<String> = listOf(
            "https://rickandmortyapi.com/api/character/1",
            "https://rickandmortyapi.com/api/character/2",
            "https://rickandmortyapi.com/api/character/3"
        ),
        url: String = "https://rickandmortyapi.com/api/location/1",
        created: String = "2017-11-10T12:42:04.162Z"
    ): LocationResponse {
        return LocationResponse(
            id = id,
            name = name,
            type = type,
            dimension = dimension,
            residents = residents,
            url = url,
            created = created
        )
    }

    private fun createApiLocationResponse(
        locationResponse: LocationResponse = createLocationResponse()
    ): ApiLocationResponse {
        return ApiLocationResponse(results = listOf(locationResponse))
    }

    private fun createResidentResponse(
        id: Int = 1,
        name: String = "Rick Sanchez",
        image: String = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
    ): ResidentResponse {
        return ResidentResponse(id = id, name = name, image = image)
    }

    @Test
    fun `getLocationByName with valid name EXPECT Success result`() = runTest {
        val dataSource = FakeRemoteLocationDataSource(
            getLocationByNameResult = Result.Success.Value(createApiLocationResponse())
        )
        val paginator = ResidentsPaginator(dataSource)
        val repository = LocationRepositoryImpl(dataSource, paginator)

        val result = repository.getLocationByName("Earth")

        assertIs<Result.Success<*>>(result)
    }

    @Test
    fun `getLocationByName with valid name EXPECT correct location id`() = runTest {
        val locationResponse = createLocationResponse(id = 42)
        val dataSource = FakeRemoteLocationDataSource(
            getLocationByNameResult = Result.Success.Value(createApiLocationResponse(locationResponse))
        )
        val paginator = ResidentsPaginator(dataSource)
        val repository = LocationRepositoryImpl(dataSource, paginator)

        val result = repository.getLocationByName("Earth")

        val location = (result as Result.Success).value
        assertEquals(42, location.id)
    }

    @Test
    fun `getLocationByName with valid name EXPECT correct location name`() = runTest {
        val locationResponse = createLocationResponse(name = "Citadel of Ricks")
        val dataSource = FakeRemoteLocationDataSource(
            getLocationByNameResult = Result.Success.Value(createApiLocationResponse(locationResponse))
        )
        val paginator = ResidentsPaginator(dataSource)
        val repository = LocationRepositoryImpl(dataSource, paginator)

        val result = repository.getLocationByName("Citadel")

        val location = (result as Result.Success).value
        assertEquals("Citadel of Ricks", location.name)
    }

    @Test
    fun `getLocationByName with valid name EXPECT correct location type`() = runTest {
        val locationResponse = createLocationResponse(type = "Space station")
        val dataSource = FakeRemoteLocationDataSource(
            getLocationByNameResult = Result.Success.Value(createApiLocationResponse(locationResponse))
        )
        val paginator = ResidentsPaginator(dataSource)
        val repository = LocationRepositoryImpl(dataSource, paginator)

        val result = repository.getLocationByName("Citadel")

        val location = (result as Result.Success).value
        assertEquals("Space station", location.type)
    }

    @Test
    fun `getLocationByName with valid name EXPECT correct location dimension`() = runTest {
        val locationResponse = createLocationResponse(dimension = "Unknown")
        val dataSource = FakeRemoteLocationDataSource(
            getLocationByNameResult = Result.Success.Value(createApiLocationResponse(locationResponse))
        )
        val paginator = ResidentsPaginator(dataSource)
        val repository = LocationRepositoryImpl(dataSource, paginator)

        val result = repository.getLocationByName("Earth")

        val location = (result as Result.Success).value
        assertEquals("Unknown", location.dimension)
    }

    @Test
    fun `getLocationByName with valid name EXPECT correct amountResidents`() = runTest {
        val residents = listOf(
            "https://rickandmortyapi.com/api/character/1",
            "https://rickandmortyapi.com/api/character/2",
            "https://rickandmortyapi.com/api/character/3",
            "https://rickandmortyapi.com/api/character/4",
            "https://rickandmortyapi.com/api/character/5"
        )
        val locationResponse = createLocationResponse(residents = residents)
        val dataSource = FakeRemoteLocationDataSource(
            getLocationByNameResult = Result.Success.Value(createApiLocationResponse(locationResponse))
        )
        val paginator = ResidentsPaginator(dataSource)
        val repository = LocationRepositoryImpl(dataSource, paginator)

        val result = repository.getLocationByName("Earth")

        val location = (result as Result.Success).value
        assertEquals(5, location.amountResidents)
    }

    @Test
    fun `getLocationByName with empty residents EXPECT amountResidents zero`() = runTest {
        val locationResponse = createLocationResponse(residents = emptyList())
        val dataSource = FakeRemoteLocationDataSource(
            getLocationByNameResult = Result.Success.Value(createApiLocationResponse(locationResponse))
        )
        val paginator = ResidentsPaginator(dataSource)
        val repository = LocationRepositoryImpl(dataSource, paginator)

        val result = repository.getLocationByName("Empty Planet")

        val location = (result as Result.Success).value
        assertEquals(0, location.amountResidents)
    }

    @Test
    fun `getLocationByName with error EXPECT Failure result`() = runTest {
        val dataSource = FakeRemoteLocationDataSource(
            getLocationByNameResult = Result.Failure.Error(Exception("Network error"))
        )
        val paginator = ResidentsPaginator(dataSource)
        val repository = LocationRepositoryImpl(dataSource, paginator)

        val result = repository.getLocationByName("NonExistent")

        assertIs<Result.Failure<*>>(result)
    }

    @Test
    fun `loadNextResidents with empty urls EXPECT Error state in residents flow`() = runTest {
        val dataSource = FakeRemoteLocationDataSource(
            getLocationByNameResult = Result.Success.Value(createApiLocationResponse(createLocationResponse(residents = emptyList())))
        )
        val paginator = ResidentsPaginator(dataSource)
        val repository = LocationRepositoryImpl(dataSource, paginator)

        repository.getLocationByName("Empty Planet")

        repository.residents.test {
            repository.loadNextResidents()

            val state = awaitItem()

            assertIs<ResidentState.Error>(state)
        }
    }

    @Test
    fun `loadNextResidents after getLocationByName EXPECT Loading then Success in residents flow`() = runTest {
        val residents = listOf("https://rickandmortyapi.com/api/character/1")
        val locationResponse = createLocationResponse(residents = residents)
        val dataSource = FakeRemoteLocationDataSource(
            getLocationByNameResult = Result.Success.Value(createApiLocationResponse(locationResponse)),
            getResidentsResult = Result.Success.Value(createResidentResponse())
        )
        val paginator = ResidentsPaginator(dataSource)
        val repository = LocationRepositoryImpl(dataSource, paginator)

        repository.getLocationByName("Earth")

        repository.residents.test {
            repository.loadNextResidents()

            val loadingState = awaitItem()
            assertIs<ResidentState.Loading>(loadingState)

            val successState = awaitItem()
            assertIs<ResidentState.Success>(successState)
        }
    }

    @Test
    fun `loadNextResidents after getLocationByName EXPECT correct residents count`() = runTest {
        val residents = listOf(
            "https://rickandmortyapi.com/api/character/1",
            "https://rickandmortyapi.com/api/character/2"
        )
        val locationResponse = createLocationResponse(residents = residents)
        val dataSource = FakeRemoteLocationDataSource(
            getLocationByNameResult = Result.Success.Value(createApiLocationResponse(locationResponse)),
            getResidentsResult = Result.Success.Value(createResidentResponse())
        )
        val paginator = ResidentsPaginator(dataSource)
        val repository = LocationRepositoryImpl(dataSource, paginator)

        repository.getLocationByName("Earth")

        repository.residents.test {
            repository.loadNextResidents()

            skipItems(1) // Skip Loading
            val successState = awaitItem() as ResidentState.Success

            assertEquals(2, successState.value.size)
        }
    }

    @Test
    fun `loadNextResidents with single resident EXPECT reached true`() = runTest {
        val residents = listOf("https://rickandmortyapi.com/api/character/1")
        val locationResponse = createLocationResponse(residents = residents)
        val dataSource = FakeRemoteLocationDataSource(
            getLocationByNameResult = Result.Success.Value(createApiLocationResponse(locationResponse)),
            getResidentsResult = Result.Success.Value(createResidentResponse())
        )
        val paginator = ResidentsPaginator(dataSource)
        val repository = LocationRepositoryImpl(dataSource, paginator)

        repository.getLocationByName("Earth")

        repository.residents.test {
            repository.loadNextResidents()

            skipItems(1) // Skip Loading
            val successState = awaitItem() as ResidentState.Success

            assertTrue(successState.reached)
        }
    }

    @Test
    fun `loadNextResidents twice after all loaded EXPECT Ended state`() = runTest {
        val residents = listOf("https://rickandmortyapi.com/api/character/1")
        val locationResponse = createLocationResponse(residents = residents)
        val dataSource = FakeRemoteLocationDataSource(
            getLocationByNameResult = Result.Success.Value(createApiLocationResponse(locationResponse)),
            getResidentsResult = Result.Success.Value(createResidentResponse())
        )
        val paginator = ResidentsPaginator(dataSource)
        val repository = LocationRepositoryImpl(dataSource, paginator)

        repository.getLocationByName("Earth")

        repository.residents.test {
            repository.loadNextResidents()
            skipItems(2) // Skip Loading + Success

            repository.loadNextResidents()
            val endedState = awaitItem()

            assertIs<ResidentState.Ended>(endedState)
        }
    }

    private class FakeRemoteLocationDataSource(
        private val getLocationByNameResult: Result<ApiLocationResponse> = Result.Failure.Error(Exception("Not set")),
        private val getResidentsResult: Result<ResidentResponse> = Result.Failure.Error(Exception("Not set"))
    ) : RemoteLocationDataSource {

        override suspend fun getLocationByName(name: String): Result<ApiLocationResponse> {
            return getLocationByNameResult
        }

        override suspend fun getResidents(id: Int): Result<ResidentResponse> {
            return getResidentsResult
        }
    }
}
