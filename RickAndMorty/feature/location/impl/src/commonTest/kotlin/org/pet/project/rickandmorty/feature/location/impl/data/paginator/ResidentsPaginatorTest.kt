package org.pet.project.rickandmorty.feature.location.impl.data.paginator

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.pet.project.rickandmorty.feature.location.impl.data.datasource.RemoteLocationDataSource
import org.pet.project.rickandmorty.feature.location.impl.data.model.ApiLocationResponse
import org.pet.project.rickandmorty.feature.location.impl.data.model.ResidentResponse
import org.pet.project.rickandmorty.library.result.Result
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class ResidentsPaginatorTest {

    private fun createResidentResponse(
        id: Int = 1,
        name: String = "Test Resident",
        image: String = "https://example.com/image.png"
    ): ResidentResponse {
        return ResidentResponse(
            id = id,
            name = name,
            image = image
        )
    }

    private fun createFakeDataSource(
        getResidentsResult: (Int) -> Result<ResidentResponse> = { Result.Success.Value(createResidentResponse(id = it)) }
    ): RemoteLocationDataSource {
        return object : RemoteLocationDataSource {
            override suspend fun getLocationByName(name: String): Result<ApiLocationResponse> {
                throw NotImplementedError("Not used in this test")
            }

            override suspend fun getResidents(id: Int): Result<ResidentResponse> {
                return getResidentsResult(id)
            }
        }
    }

    @Test
    fun `loadNextResidents with empty urls EXPECT Error state`() = runTest {
        val dataSource = createFakeDataSource()
        val paginator = ResidentsPaginator(dataSource)
        paginator.setResidentsUrls(emptyList())

        paginator.residentsFlow.test {
            paginator.loadNextResidents()

            val state = awaitItem()

            assertIs<RequestResidentState.Error>(state)
        }
    }

    @Test
    fun `loadNextResidents with urls EXPECT Loading state emitted first`() = runTest {
        val dataSource = createFakeDataSource()
        val paginator = ResidentsPaginator(dataSource)
        paginator.setResidentsUrls(listOf("https://rickandmortyapi.com/api/character/1"))

        paginator.residentsFlow.test {
            paginator.loadNextResidents()

            val loadingState = awaitItem()
            cancelAndIgnoreRemainingEvents()

            assertIs<RequestResidentState.Loading>(loadingState)
        }
    }

    @Test
    fun `loadNextResidents with single url EXPECT Success state`() = runTest {
        val dataSource = createFakeDataSource()
        val paginator = ResidentsPaginator(dataSource)
        paginator.setResidentsUrls(listOf("https://rickandmortyapi.com/api/character/1"))

        paginator.residentsFlow.test {
            paginator.loadNextResidents()
            skipItems(1) // Skip Loading

            val state = awaitItem()

            assertIs<RequestResidentState.Success>(state)
        }
    }

    @Test
    fun `loadNextResidents with single url EXPECT reached is true`() = runTest {
        val dataSource = createFakeDataSource()
        val paginator = ResidentsPaginator(dataSource)
        paginator.setResidentsUrls(listOf("https://rickandmortyapi.com/api/character/1"))

        paginator.residentsFlow.test {
            paginator.loadNextResidents()
            skipItems(1) // Skip Loading

            val state = awaitItem() as RequestResidentState.Success

            assertTrue(state.reached)
        }
    }

    @Test
    fun `loadNextResidents with single url EXPECT value list size is 1`() = runTest {
        val dataSource = createFakeDataSource()
        val paginator = ResidentsPaginator(dataSource)
        paginator.setResidentsUrls(listOf("https://rickandmortyapi.com/api/character/1"))

        paginator.residentsFlow.test {
            paginator.loadNextResidents()
            skipItems(1) // Skip Loading

            val state = awaitItem() as RequestResidentState.Success

            assertEquals(1, state.value.size)
        }
    }

    @Test
    fun `loadNextResidents with 10 urls EXPECT value list size is 10`() = runTest {
        val dataSource = createFakeDataSource()
        val paginator = ResidentsPaginator(dataSource)
        val urls = (1..10).map { "https://rickandmortyapi.com/api/character/$it" }
        paginator.setResidentsUrls(urls)

        paginator.residentsFlow.test {
            paginator.loadNextResidents()
            skipItems(1) // Skip Loading

            val state = awaitItem() as RequestResidentState.Success

            assertEquals(10, state.value.size)
        }
    }

    @Test
    fun `loadNextResidents with 10 urls EXPECT reached is true`() = runTest {
        val dataSource = createFakeDataSource()
        val paginator = ResidentsPaginator(dataSource)
        val urls = (1..10).map { "https://rickandmortyapi.com/api/character/$it" }
        paginator.setResidentsUrls(urls)

        paginator.residentsFlow.test {
            paginator.loadNextResidents()
            skipItems(1) // Skip Loading

            val state = awaitItem() as RequestResidentState.Success

            assertTrue(state.reached)
        }
    }

    @Test
    fun `loadNextResidents with 25 urls EXPECT first page value list size is 20`() = runTest {
        val dataSource = createFakeDataSource()
        val paginator = ResidentsPaginator(dataSource)
        val urls = (1..25).map { "https://rickandmortyapi.com/api/character/$it" }
        paginator.setResidentsUrls(urls)

        paginator.residentsFlow.test {
            paginator.loadNextResidents()
            skipItems(1) // Skip Loading

            val state = awaitItem() as RequestResidentState.Success

            assertEquals(20, state.value.size)
        }
    }

    @Test
    fun `loadNextResidents with 25 urls EXPECT first page reached is false`() = runTest {
        val dataSource = createFakeDataSource()
        val paginator = ResidentsPaginator(dataSource)
        val urls = (1..25).map { "https://rickandmortyapi.com/api/character/$it" }
        paginator.setResidentsUrls(urls)

        paginator.residentsFlow.test {
            paginator.loadNextResidents()
            skipItems(1) // Skip Loading

            val state = awaitItem() as RequestResidentState.Success

            assertEquals(false, state.reached)
        }
    }

    @Test
    fun `loadNextResidents twice with 25 urls EXPECT second page value list size is 5`() = runTest {
        val dataSource = createFakeDataSource()
        val paginator = ResidentsPaginator(dataSource)
        val urls = (1..25).map { "https://rickandmortyapi.com/api/character/$it" }
        paginator.setResidentsUrls(urls)

        paginator.residentsFlow.test {
            paginator.loadNextResidents()
            skipItems(2) // Skip Loading + Success

            paginator.loadNextResidents()
            skipItems(1) // Skip Loading

            val state = awaitItem() as RequestResidentState.Success

            assertEquals(5, state.value.size)
        }
    }

    @Test
    fun `loadNextResidents twice with 25 urls EXPECT second page reached is true`() = runTest {
        val dataSource = createFakeDataSource()
        val paginator = ResidentsPaginator(dataSource)
        val urls = (1..25).map { "https://rickandmortyapi.com/api/character/$it" }
        paginator.setResidentsUrls(urls)

        paginator.residentsFlow.test {
            paginator.loadNextResidents()
            skipItems(2) // Skip Loading + Success

            paginator.loadNextResidents()
            skipItems(1) // Skip Loading

            val state = awaitItem() as RequestResidentState.Success

            assertTrue(state.reached)
        }
    }

    @Test
    fun `loadNextResidents three times with 25 urls EXPECT Ended state`() = runTest {
        val dataSource = createFakeDataSource()
        val paginator = ResidentsPaginator(dataSource)
        val urls = (1..25).map { "https://rickandmortyapi.com/api/character/$it" }
        paginator.setResidentsUrls(urls)

        paginator.residentsFlow.test {
            paginator.loadNextResidents()
            skipItems(2) // Skip Loading + Success

            paginator.loadNextResidents()
            skipItems(2) // Skip Loading + Success

            paginator.loadNextResidents()

            val state = awaitItem()

            assertIs<RequestResidentState.Ended>(state)
        }
    }

    @Test
    fun `loadNextResidents after all items loaded EXPECT Ended state`() = runTest {
        val dataSource = createFakeDataSource()
        val paginator = ResidentsPaginator(dataSource)
        val urls = (1..5).map { "https://rickandmortyapi.com/api/character/$it" }
        paginator.setResidentsUrls(urls)

        paginator.residentsFlow.test {
            paginator.loadNextResidents()
            skipItems(2) // Skip Loading + Success

            paginator.loadNextResidents()

            val state = awaitItem()

            assertIs<RequestResidentState.Ended>(state)
        }
    }

    @Test
    fun `loadNextResidents with exactly 20 urls EXPECT reached is true`() = runTest {
        val dataSource = createFakeDataSource()
        val paginator = ResidentsPaginator(dataSource)
        val urls = (1..20).map { "https://rickandmortyapi.com/api/character/$it" }
        paginator.setResidentsUrls(urls)

        paginator.residentsFlow.test {
            paginator.loadNextResidents()
            skipItems(1) // Skip Loading

            val state = awaitItem() as RequestResidentState.Success

            assertTrue(state.reached)
        }
    }

    @Test
    fun `loadNextResidents extracts correct character id from url`() = runTest {
        var capturedId: Int? = null
        val dataSource = createFakeDataSource { id ->
            capturedId = id
            Result.Success.Value(createResidentResponse(id = id))
        }
        val paginator = ResidentsPaginator(dataSource)
        paginator.setResidentsUrls(listOf("https://rickandmortyapi.com/api/character/42"))

        paginator.residentsFlow.test {
            paginator.loadNextResidents()
            skipItems(2)

            assertEquals(42, capturedId)
        }
    }

    @Test
    fun `loadNextResidents with mixed success and failure EXPECT all results in value list`() = runTest {
        var callCount = 0
        val dataSource = createFakeDataSource { id ->
            callCount++
            if (callCount == 2) {
                Result.Failure.Error(Exception("Network error"))
            } else {
                Result.Success.Value(createResidentResponse(id = id))
            }
        }
        val paginator = ResidentsPaginator(dataSource)
        val urls = (1..3).map { "https://rickandmortyapi.com/api/character/$it" }
        paginator.setResidentsUrls(urls)

        paginator.residentsFlow.test {
            paginator.loadNextResidents()
            skipItems(1) // Skip Loading

            val state = awaitItem() as RequestResidentState.Success

            assertEquals(3, state.value.size)
        }
    }

    @Test
    fun `loadNextResidents with failure result EXPECT failure in value list`() = runTest {
        val dataSource = createFakeDataSource { _ ->
            Result.Failure.Error(Exception("Network error"))
        }
        val paginator = ResidentsPaginator(dataSource)
        paginator.setResidentsUrls(listOf("https://rickandmortyapi.com/api/character/1"))

        paginator.residentsFlow.test {
            paginator.loadNextResidents()
            skipItems(1) // Skip Loading

            val state = awaitItem() as RequestResidentState.Success

            assertIs<Result.Failure<*>>(state.value.first())
        }
    }

    @Test
    fun `loadNextResidents with success result EXPECT success in value list`() = runTest {
        val dataSource = createFakeDataSource()
        val paginator = ResidentsPaginator(dataSource)
        paginator.setResidentsUrls(listOf("https://rickandmortyapi.com/api/character/1"))

        paginator.residentsFlow.test {
            paginator.loadNextResidents()
            skipItems(1) // Skip Loading

            val state = awaitItem() as RequestResidentState.Success

            assertIs<Result.Success<*>>(state.value.first())
        }
    }
}
