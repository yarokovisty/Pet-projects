package org.pet.project.rickandmorty.feature.episode.impl.data.repository

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.pet.project.rickandmorty.common.data.InfoResponse
import org.pet.project.rickandmorty.feature.episode.api.domain.entity.EpisodeState
import org.pet.project.rickandmorty.feature.episode.api.domain.entity.Season
import org.pet.project.rickandmorty.feature.episode.impl.data.datasource.FakeEpisodeRemoteDataSource
import org.pet.project.rickandmorty.feature.episode.impl.data.model.EpisodeListResponse
import org.pet.project.rickandmorty.feature.episode.impl.data.model.EpisodeResponse
import org.pet.project.rickandmorty.feature.episode.impl.data.paginator.EpisodePaginator
import org.pet.project.rickandmorty.library.result.Result
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class EpisodeRepositoryImplTest {

    private lateinit var fakeDataSource: FakeEpisodeRemoteDataSource
    private lateinit var paginator: EpisodePaginator
    private lateinit var repository: EpisodeRepositoryImpl

    @BeforeTest
    fun setUp() {
        fakeDataSource = FakeEpisodeRemoteDataSource()
        paginator = EpisodePaginator(fakeDataSource)
        repository = EpisodeRepositoryImpl(fakeDataSource, paginator)
    }

    // episodes flow tests

    @Test
    fun `episodes flow emits Initial state initially EXPECT EpisodeState Initial`() = runTest {
        val state = repository.episodes.first()

        assertIs<EpisodeState.Initial>(state)
    }

    @Test
    fun `episodes flow after loadEpisodes success EXPECT EpisodeState Success`() = runTest {
        fakeDataSource.setPageResponse(1, createSuccessPageResponse())

        repository.loadEpisodes()
        val state = repository.episodes.first { it is EpisodeState.Success || it is EpisodeState.End }

        assertTrue(state is EpisodeState.Success || state is EpisodeState.End)
    }

    @Test
    fun `episodes flow after loadEpisodes success EXPECT seasons map not empty`() = runTest {
        fakeDataSource.setPageResponse(1, createSuccessPageResponse())

        repository.loadEpisodes()
        val state = repository.episodes.first { it is EpisodeState.Success }
        assertIs<EpisodeState.Success>(state)

        assertTrue(state.value.isNotEmpty())
    }

    @Test
    fun `episodes flow after loadEpisodes error EXPECT EpisodeState Error`() = runTest {
        val exception = RuntimeException("Network error")
        fakeDataSource.setPageResponse(1, Result.Failure.Error(exception))

        repository.loadEpisodes()
        val state = repository.episodes.first { it is EpisodeState.Error }

        assertIs<EpisodeState.Error>(state)
    }

    @Test
    fun `episodes flow after loadEpisodes error EXPECT correct exception`() = runTest {
        val exception = RuntimeException("Network error")
        fakeDataSource.setPageResponse(1, Result.Failure.Error(exception))

        repository.loadEpisodes()
        val state = repository.episodes.first { it is EpisodeState.Error }
        assertIs<EpisodeState.Error>(state)

        assertEquals(exception, state.throwable)
    }

    @Test
    fun `episodes flow after reaching end EXPECT EpisodeState End`() = runTest {
        val singlePageResponse = EpisodeListResponse(
            info = InfoResponse(count = 1, pages = 1, next = null, prev = null),
            results = listOf(createEpisodeResponse(1))
        )
        fakeDataSource.setPageResponse(1, Result.Success.Value(singlePageResponse))

        repository.loadEpisodes()
        val state = repository.episodes.first { it is EpisodeState.End }

        assertIs<EpisodeState.End>(state)
    }

    // loadEpisodes tests

    @Test
    fun `loadEpisodes delegates to paginator EXPECT data source called`() = runTest {
        fakeDataSource.setPageResponse(1, createSuccessPageResponse())

        repository.loadEpisodes()

        assertEquals(1, fakeDataSource.getEpisodesByPageCallCount)
    }

    @Test
    fun `loadEpisodes multiple times EXPECT multiple pages requested`() = runTest {
        fakeDataSource.setPageResponse(1, createSuccessPageResponse())
        fakeDataSource.setPageResponse(2, createSuccessPageResponse(page = 2))

        repository.loadEpisodes()
        repository.loadEpisodes()

        assertEquals(2, fakeDataSource.getEpisodesByPageCallCount)
    }

    // getEpisodes tests

    @Test
    fun `getEpisodes with valid ids EXPECT Result Success`() = runTest {
        fakeDataSource.setEpisodesResponse(Result.Success.Value(createEpisodeResponseList()))

        val result = repository.getEpisodes(listOf(1, 2, 3))

        assertIs<Result.Success<List<Season>>>(result)
    }

    @Test
    fun `getEpisodes with valid ids EXPECT correct seasons count`() = runTest {
        fakeDataSource.setEpisodesResponse(Result.Success.Value(createEpisodeResponseList()))

        val result = repository.getEpisodes(listOf(1, 2, 3))
        val seasons = assertIs<Result.Success<List<Season>>>(result).value

        assertEquals(2, seasons.size)
    }

    @Test
    fun `getEpisodes with valid ids EXPECT first season number correct`() = runTest {
        fakeDataSource.setEpisodesResponse(Result.Success.Value(createEpisodeResponseList()))

        val result = repository.getEpisodes(listOf(1, 2, 3))
        val seasons = assertIs<Result.Success<List<Season>>>(result).value

        assertEquals(1, seasons[0].num)
    }

    @Test
    fun `getEpisodes with valid ids EXPECT first season has correct episode count`() = runTest {
        fakeDataSource.setEpisodesResponse(Result.Success.Value(createEpisodeResponseList()))

        val result = repository.getEpisodes(listOf(1, 2, 3))
        val seasons = assertIs<Result.Success<List<Season>>>(result).value

        assertEquals(2, seasons[0].amountEpisodes)
    }

    @Test
    fun `getEpisodes with valid ids EXPECT second season number correct`() = runTest {
        fakeDataSource.setEpisodesResponse(Result.Success.Value(createEpisodeResponseList()))

        val result = repository.getEpisodes(listOf(1, 2, 3))
        val seasons = assertIs<Result.Success<List<Season>>>(result).value

        assertEquals(2, seasons[1].num)
    }

    @Test
    fun `getEpisodes with valid ids EXPECT second season has correct episode count`() = runTest {
        fakeDataSource.setEpisodesResponse(Result.Success.Value(createEpisodeResponseList()))

        val result = repository.getEpisodes(listOf(1, 2, 3))
        val seasons = assertIs<Result.Success<List<Season>>>(result).value

        assertEquals(1, seasons[1].amountEpisodes)
    }

    @Test
    fun `getEpisodes with empty ids EXPECT empty seasons list`() = runTest {
        fakeDataSource.setEpisodesResponse(Result.Success.Value(emptyList()))

        val result = repository.getEpisodes(emptyList())
        val seasons = assertIs<Result.Success<List<Season>>>(result).value

        assertTrue(seasons.isEmpty())
    }

    @Test
    fun `getEpisodes with error EXPECT Result Failure`() = runTest {
        val exception = RuntimeException("Episodes not found")
        fakeDataSource.setEpisodesResponse(Result.Failure.Error(exception))

        val result = repository.getEpisodes(listOf(1, 2, 3))

        assertIs<Result.Failure<*>>(result)
    }

    @Test
    fun `getEpisodes with error EXPECT correct exception`() = runTest {
        val exception = RuntimeException("Episodes not found")
        fakeDataSource.setEpisodesResponse(Result.Failure.Error(exception))

        val result = repository.getEpisodes(listOf(1, 2, 3))
        val failure = assertIs<Result.Failure<*>>(result)

        assertEquals(exception, failure.error)
    }

    @Test
    fun `getEpisodes EXPECT correct ids passed to data source`() = runTest {
        val ids = listOf(1, 5, 10)
        fakeDataSource.setEpisodesResponse(Result.Success.Value(emptyList()))

        repository.getEpisodes(ids)

        assertEquals(ids, fakeDataSource.lastRequestedIds)
    }

    @Test
    fun `getEpisodes EXPECT data source called once`() = runTest {
        fakeDataSource.setEpisodesResponse(Result.Success.Value(emptyList()))

        repository.getEpisodes(listOf(1, 2, 3))

        assertEquals(1, fakeDataSource.getEpisodesCallCount)
    }

    @Test
    fun `getEpisodes with single episode EXPECT single season`() = runTest {
        val singleEpisode = listOf(createEpisodeResponse(1, "S01E01"))
        fakeDataSource.setEpisodesResponse(Result.Success.Value(singleEpisode))

        val result = repository.getEpisodes(listOf(1))
        val seasons = assertIs<Result.Success<List<Season>>>(result).value

        assertEquals(1, seasons.size)
    }

    @Test
    fun `getEpisodes with single episode EXPECT correct amount in season`() = runTest {
        val singleEpisode = listOf(createEpisodeResponse(1, "S01E01"))
        fakeDataSource.setEpisodesResponse(Result.Success.Value(singleEpisode))

        val result = repository.getEpisodes(listOf(1))
        val seasons = assertIs<Result.Success<List<Season>>>(result).value

        assertEquals(1, seasons[0].amountEpisodes)
    }

    // Helper functions

    private fun createEpisodeResponse(id: Int, episode: String = "S01E01"): EpisodeResponse {
        return EpisodeResponse(
            id = id,
            name = "Episode $id",
            airDate = "December 2, 2013",
            episode = episode
        )
    }

    private fun createEpisodeResponseList(): List<EpisodeResponse> {
        return listOf(
            EpisodeResponse(id = 1, name = "Pilot", airDate = "December 2, 2013", episode = "S01E01"),
            EpisodeResponse(id = 2, name = "Lawnmower Dog", airDate = "December 9, 2013", episode = "S01E02"),
            EpisodeResponse(id = 12, name = "A Rickle in Time", airDate = "July 26, 2015", episode = "S02E01")
        )
    }

    private fun createSuccessPageResponse(page: Int = 1): Result<EpisodeListResponse> {
        val nextUrl = if (page < 3) "https://rickandmortyapi.com/api/episode?page=${page + 1}" else null
        return Result.Success.Value(
            EpisodeListResponse(
                info = InfoResponse(count = 51, pages = 3, next = nextUrl, prev = null),
                results = listOf(createEpisodeResponse(page))
            )
        )
    }
}
