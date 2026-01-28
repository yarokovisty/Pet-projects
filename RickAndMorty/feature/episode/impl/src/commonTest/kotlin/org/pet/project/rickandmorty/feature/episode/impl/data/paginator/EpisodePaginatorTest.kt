package org.pet.project.rickandmorty.feature.episode.impl.data.paginator

import kotlinx.coroutines.test.runTest
import org.pet.project.rickandmorty.common.data.InfoResponse
import org.pet.project.rickandmorty.common.data.PaginatorState
import org.pet.project.rickandmorty.feature.episode.impl.data.datasource.FakeEpisodeRemoteDataSource
import org.pet.project.rickandmorty.feature.episode.impl.data.model.EpisodeListResponse
import org.pet.project.rickandmorty.feature.episode.impl.data.model.EpisodeResponse
import org.pet.project.rickandmorty.library.result.Result
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class EpisodePaginatorTest {

    private lateinit var fakeDataSource: FakeEpisodeRemoteDataSource
    private lateinit var paginator: EpisodePaginator

    @BeforeTest
    fun setUp() {
        fakeDataSource = FakeEpisodeRemoteDataSource()
        paginator = EpisodePaginator(fakeDataSource)
    }

    @Test
    fun `loadItems first time EXPECT delegate to remoteDataSource getEpisodesByPage`() = runTest {
        val episode = createEpisodeResponse(1, "Pilot")
        val page1 = createPage(1, 3, listOf(episode))
        fakeDataSource.setPageResponse(1, Result.Success.Value(page1))

        paginator.loadItems()

        assertEquals(1, fakeDataSource.getEpisodesByPageCallCount)
    }

    @Test
    fun `loadItems first time EXPECT call page 1`() = runTest {
        val episode = createEpisodeResponse(1, "Pilot")
        val page1 = createPage(1, 3, listOf(episode))
        fakeDataSource.setPageResponse(1, Result.Success.Value(page1))

        paginator.loadItems()

        assertEquals(1, fakeDataSource.lastRequestedPage)
    }

    @Test
    fun `getNextKey with available next page EXPECT return next page from info`() = runTest {
        val episode = createEpisodeResponse(1, "Pilot")
        val page1 = createPage(1, 3, listOf(episode))
        fakeDataSource.setPageResponse(1, Result.Success.Value(page1))

        paginator.loadItems()
        val state = paginator.paginationFlow.value
        assertIs<PaginatorState.Success<EpisodeListResponse>>(state)

        assertEquals(2, state.value.info.getNextPage())
    }

    @Test
    fun `getNextKey with null next page EXPECT return currentKey plus one`() = runTest {
        val episode = createEpisodeResponse(1, "Pilot")
        val pageWithNullNext = EpisodeListResponse(
            info = InfoResponse(count = 51, pages = 3, next = null, prev = null),
            results = listOf(episode)
        )
        val page2 = createPage(2, 3, listOf(episode))
        fakeDataSource.setPageResponse(1, Result.Success.Value(pageWithNullNext))
        fakeDataSource.setPageResponse(2, Result.Success.Value(page2))

        paginator.loadItems()
        paginator.loadItems()

        assertEquals(2, fakeDataSource.lastRequestedPage)
    }

    @Test
    fun `checkEndReached when currentKey greater than allPages EXPECT End state`() = runTest {
        val episode = createEpisodeResponse(1, "Pilot")
        val smallPage = createPage(1, 1, listOf(episode), hasNext = false)
        fakeDataSource.setPageResponse(1, Result.Success.Value(smallPage))
        fakeDataSource.setDefaultPageResponse(Result.Success.Value(smallPage))

        repeat(3) { paginator.loadItems() }
        val state = paginator.paginationFlow.value

        assertIs<PaginatorState.End>(state)
    }

    @Test
    fun `checkEndReached when currentKey less than allPages EXPECT Success state`() = runTest {
        val episode = createEpisodeResponse(1, "Pilot")
        val page1 = createPage(1, 3, listOf(episode))
        fakeDataSource.setPageResponse(1, Result.Success.Value(page1))

        paginator.loadItems()
        val state = paginator.paginationFlow.value

        assertIs<PaginatorState.Success<EpisodeListResponse>>(state)
    }

    @Test
    fun `loadItems initial state EXPECT Initial`() = runTest {
        val state = paginator.paginationFlow.value

        assertIs<PaginatorState.Initial>(state)
    }

    @Test
    fun `loadItems with success EXPECT Success state`() = runTest {
        val episode = createEpisodeResponse(1, "Pilot")
        val page1 = createPage(1, 3, listOf(episode))
        fakeDataSource.setPageResponse(1, Result.Success.Value(page1))

        paginator.loadItems()
        val state = paginator.paginationFlow.value

        assertIs<PaginatorState.Success<EpisodeListResponse>>(state)
    }

    @Test
    fun `loadItems with error EXPECT Error state`() = runTest {
        val exception = RuntimeException("Network error")
        fakeDataSource.setPageResponse(1, Result.Failure.Error(exception))

        paginator.loadItems()
        val state = paginator.paginationFlow.value

        assertIs<PaginatorState.Error>(state)
    }

    @Test
    fun `loadItems with error EXPECT correct exception`() = runTest {
        val exception = RuntimeException("Network error")
        fakeDataSource.setPageResponse(1, Result.Failure.Error(exception))

        paginator.loadItems()
        val state = paginator.paginationFlow.value
        assertIs<PaginatorState.Error>(state)

        assertEquals(exception, state.throwable)
    }

    @Test
    fun `loadItems when end reached EXPECT no additional remoteDataSource call`() = runTest {
        val episode = createEpisodeResponse(1, "Pilot")
        val singlePage = createPage(1, 1, listOf(episode), hasNext = false)
        fakeDataSource.setPageResponse(1, Result.Success.Value(singlePage))

        paginator.loadItems()
        val callCountAfterFirst = fakeDataSource.getEpisodesByPageCallCount

        paginator.loadItems()
        val callCountAfterSecond = fakeDataSource.getEpisodesByPageCallCount

        assertEquals(callCountAfterFirst, callCountAfterSecond)
    }

    @Test
    fun `loadItems multiple times EXPECT correct page sequence`() = runTest {
        val ep1 = createEpisodeResponse(1, "Pilot")
        val ep2 = createEpisodeResponse(2, "Lawnmower Dog")
        val page1 = createPage(1, 3, listOf(ep1))
        val page2 = createPage(2, 3, listOf(ep2))
        fakeDataSource.setPageResponse(1, Result.Success.Value(page1))
        fakeDataSource.setPageResponse(2, Result.Success.Value(page2))

        paginator.loadItems()
        paginator.loadItems()

        assertEquals(2, fakeDataSource.lastRequestedPage)
    }

    @Test
    fun `loadItems multiple times EXPECT correct episode on second page`() = runTest {
        val ep1 = createEpisodeResponse(1, "Pilot")
        val ep2 = createEpisodeResponse(2, "Lawnmower Dog")
        val page1 = createPage(1, 3, listOf(ep1))
        val page2 = createPage(2, 3, listOf(ep2))
        fakeDataSource.setPageResponse(1, Result.Success.Value(page1))
        fakeDataSource.setPageResponse(2, Result.Success.Value(page2))

        paginator.loadItems()
        paginator.loadItems()
        val state = paginator.paginationFlow.value
        assertIs<PaginatorState.Success<EpisodeListResponse>>(state)

        assertEquals("Lawnmower Dog", state.value.results.first().name)
    }

    @Test
    fun `loadItems reaching last page EXPECT End state`() = runTest {
        val episode = createEpisodeResponse(1, "Pilot")
        val singlePage = createPage(1, 1, listOf(episode), hasNext = false)
        fakeDataSource.setPageResponse(1, Result.Success.Value(singlePage))

        paginator.loadItems()
        val state = paginator.paginationFlow.value

        assertIs<PaginatorState.End>(state)
    }

    @Test
    fun `loadItems with success EXPECT correct count value`() = runTest {
        val episode = createEpisodeResponse(1, "Pilot")
        val page1 = createPage(1, 3, listOf(episode))
        fakeDataSource.setPageResponse(1, Result.Success.Value(page1))

        paginator.loadItems()
        val state = paginator.paginationFlow.value
        assertIs<PaginatorState.Success<EpisodeListResponse>>(state)

        assertEquals(3, state.value.info.count)
    }

    @Test
    fun `loadItems after error EXPECT can retry successfully`() = runTest {
        val episode = createEpisodeResponse(1, "Pilot")
        val page1 = createPage(1, 3, listOf(episode))
        val exception = RuntimeException("Network error")

        fakeDataSource.setPageResponse(1, Result.Failure.Error(exception))
        paginator.loadItems()
        assertIs<PaginatorState.Error>(paginator.paginationFlow.value)

        fakeDataSource.setPageResponse(1, Result.Success.Value(page1))
        paginator.loadItems()
        val state = paginator.paginationFlow.value

        assertIs<PaginatorState.Success<EpisodeListResponse>>(state)
    }

    @Test
    fun `success response EXPECT correct pages count`() = runTest {
        val episode = createEpisodeResponse(1, "Pilot")
        val page1 = createPage(1, 3, listOf(episode))
        fakeDataSource.setPageResponse(1, Result.Success.Value(page1))

        paginator.loadItems()
        val state = paginator.paginationFlow.value
        assertIs<PaginatorState.Success<EpisodeListResponse>>(state)

        assertEquals(3, state.value.info.pages)
    }

    @Test
    fun `success response EXPECT correct episode name`() = runTest {
        val episode = createEpisodeResponse(1, "Pilot")
        val page1 = createPage(1, 3, listOf(episode))
        fakeDataSource.setPageResponse(1, Result.Success.Value(page1))

        paginator.loadItems()
        val state = paginator.paginationFlow.value
        assertIs<PaginatorState.Success<EpisodeListResponse>>(state)

        assertEquals("Pilot", state.value.results.first().name)
    }

    @Test
    fun `success response EXPECT correct results size`() = runTest {
        val episode = createEpisodeResponse(1, "Pilot")
        val page1 = createPage(1, 3, listOf(episode))
        fakeDataSource.setPageResponse(1, Result.Success.Value(page1))

        paginator.loadItems()
        val state = paginator.paginationFlow.value
        assertIs<PaginatorState.Success<EpisodeListResponse>>(state)

        assertEquals(1, state.value.results.size)
    }

    @Test
    fun `error response EXPECT correct exception message`() = runTest {
        val exception = IllegalStateException("Test exception")
        fakeDataSource.setPageResponse(1, Result.Failure.Error(exception))

        paginator.loadItems()
        val state = paginator.paginationFlow.value
        assertIs<PaginatorState.Error>(state)

        assertEquals("Test exception", state.throwable.message)
    }

    // Helper functions

    private fun createEpisodeResponse(id: Int, name: String): EpisodeResponse {
        return EpisodeResponse(
            id = id,
            name = name,
            airDate = "December 2, 2013",
            episode = "S01E${id.toString().padStart(2, '0')}"
        )
    }

    private fun createPage(
        page: Int,
        totalPages: Int,
        episodes: List<EpisodeResponse>,
        hasNext: Boolean = true
    ): EpisodeListResponse {
        val nextUrl = if (hasNext && page < totalPages) {
            "https://rickandmortyapi.com/api/episode?page=${page + 1}"
        } else null
        val prevUrl = if (page > 1) {
            "https://rickandmortyapi.com/api/episode?page=${page - 1}"
        } else null

        return EpisodeListResponse(
            info = InfoResponse(
                count = episodes.size * totalPages,
                pages = totalPages,
                next = nextUrl,
                prev = prevUrl
            ),
            results = episodes
        )
    }
}
