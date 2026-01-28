package org.pet.project.rickandmorty.feature.episode.impl.data.mapper

import org.pet.project.rickandmorty.common.data.InfoResponse
import org.pet.project.rickandmorty.common.data.PaginatorState
import org.pet.project.rickandmorty.feature.episode.api.domain.entity.EpisodeState
import org.pet.project.rickandmorty.feature.episode.impl.data.model.EpisodeListResponse
import org.pet.project.rickandmorty.feature.episode.impl.data.model.EpisodeResponse
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertIs
import kotlin.test.assertTrue

class EpisodeMapperTest {

    // EpisodeResponse.toItem() tests

    @Test
    fun `toItem with valid episode response EXPECT correct episode id`() {
        val response = createEpisodeResponse(id = 1)

        val result = response.toItem()

        assertEquals(1, result.id)
    }

    @Test
    fun `toItem with valid episode response EXPECT correct name`() {
        val response = createEpisodeResponse(name = "Pilot")

        val result = response.toItem()

        assertEquals("Pilot", result.name)
    }

    @Test
    fun `toItem with valid episode response EXPECT correct air date`() {
        val response = createEpisodeResponse(airDate = "December 2, 2013")

        val result = response.toItem()

        assertEquals("December 2, 2013", result.airDate)
    }

    @Test
    fun `toItem with S01E01 EXPECT season 1`() {
        val response = createEpisodeResponse(episode = "S01E01")

        val result = response.toItem()

        assertEquals(1, result.season)
    }

    @Test
    fun `toItem with S01E01 EXPECT episode 1`() {
        val response = createEpisodeResponse(episode = "S01E01")

        val result = response.toItem()

        assertEquals(1, result.seria)
    }

    @Test
    fun `toItem with S02E05 EXPECT season 2`() {
        val response = createEpisodeResponse(episode = "S02E05")

        val result = response.toItem()

        assertEquals(2, result.season)
    }

    @Test
    fun `toItem with S02E05 EXPECT episode 5`() {
        val response = createEpisodeResponse(episode = "S02E05")

        val result = response.toItem()

        assertEquals(5, result.seria)
    }

    @Test
    fun `toItem with S03E10 EXPECT season 3`() {
        val response = createEpisodeResponse(episode = "S03E10")

        val result = response.toItem()

        assertEquals(3, result.season)
    }

    @Test
    fun `toItem with S03E10 EXPECT episode 10`() {
        val response = createEpisodeResponse(episode = "S03E10")

        val result = response.toItem()

        assertEquals(10, result.seria)
    }

    @Test
    fun `toItem with lowercase s01e01 EXPECT season 1`() {
        val response = createEpisodeResponse(episode = "s01e01")

        val result = response.toItem()

        assertEquals(1, result.season)
    }

    @Test
    fun `toItem with lowercase s01e01 EXPECT episode 1`() {
        val response = createEpisodeResponse(episode = "s01e01")

        val result = response.toItem()

        assertEquals(1, result.seria)
    }

    @Test
    fun `toItem with invalid episode format EXPECT IllegalStateException`() {
        val response = createEpisodeResponse(episode = "INVALID")

        assertFailsWith<IllegalStateException> {
            response.toItem()
        }
    }

    @Test
    fun `toItem with empty episode string EXPECT IllegalStateException`() {
        val response = createEpisodeResponse(episode = "")

        assertFailsWith<IllegalStateException> {
            response.toItem()
        }
    }

    // List<EpisodeResponse>.toSeasons() tests

    @Test
    fun `toSeasons with empty list EXPECT empty result`() {
        val episodes = emptyList<EpisodeResponse>()

        val result = episodes.toSeasons()

        assertTrue(result.isEmpty())
    }

    @Test
    fun `toSeasons with single episode EXPECT one season`() {
        val episodes = listOf(createEpisodeResponse(episode = "S01E01"))

        val result = episodes.toSeasons()

        assertEquals(1, result.size)
    }

    @Test
    fun `toSeasons with single episode EXPECT correct season number`() {
        val episodes = listOf(createEpisodeResponse(episode = "S01E01"))

        val result = episodes.toSeasons()

        assertEquals(1, result[0].num)
    }

    @Test
    fun `toSeasons with single episode EXPECT correct episode count`() {
        val episodes = listOf(createEpisodeResponse(episode = "S01E01"))

        val result = episodes.toSeasons()

        assertEquals(1, result[0].amountEpisodes)
    }

    @Test
    fun `toSeasons with multiple episodes same season EXPECT one season`() {
        val episodes = listOf(
            createEpisodeResponse(id = 1, episode = "S01E01"),
            createEpisodeResponse(id = 2, episode = "S01E02"),
            createEpisodeResponse(id = 3, episode = "S01E03")
        )

        val result = episodes.toSeasons()

        assertEquals(1, result.size)
    }

    @Test
    fun `toSeasons with multiple episodes same season EXPECT correct episode count`() {
        val episodes = listOf(
            createEpisodeResponse(id = 1, episode = "S01E01"),
            createEpisodeResponse(id = 2, episode = "S01E02"),
            createEpisodeResponse(id = 3, episode = "S01E03")
        )

        val result = episodes.toSeasons()

        assertEquals(3, result[0].amountEpisodes)
    }

    @Test
    fun `toSeasons with episodes from two seasons EXPECT two seasons`() {
        val episodes = listOf(
            createEpisodeResponse(id = 1, episode = "S01E01"),
            createEpisodeResponse(id = 2, episode = "S01E02"),
            createEpisodeResponse(id = 12, episode = "S02E01"),
            createEpisodeResponse(id = 13, episode = "S02E02")
        )

        val result = episodes.toSeasons()

        assertEquals(2, result.size)
    }

    @Test
    fun `toSeasons with episodes from two seasons EXPECT first season has 2 episodes`() {
        val episodes = listOf(
            createEpisodeResponse(id = 1, episode = "S01E01"),
            createEpisodeResponse(id = 2, episode = "S01E02"),
            createEpisodeResponse(id = 12, episode = "S02E01"),
            createEpisodeResponse(id = 13, episode = "S02E02")
        )

        val result = episodes.toSeasons()

        assertEquals(2, result.find { it.num == 1 }?.amountEpisodes)
    }

    @Test
    fun `toSeasons with episodes from two seasons EXPECT second season has 2 episodes`() {
        val episodes = listOf(
            createEpisodeResponse(id = 1, episode = "S01E01"),
            createEpisodeResponse(id = 2, episode = "S01E02"),
            createEpisodeResponse(id = 12, episode = "S02E01"),
            createEpisodeResponse(id = 13, episode = "S02E02")
        )

        val result = episodes.toSeasons()

        assertEquals(2, result.find { it.num == 2 }?.amountEpisodes)
    }

    // EpisodeListResponse.toItem() tests

    @Test
    fun `EpisodeListResponse toItem with empty results EXPECT empty map`() {
        val response = createEpisodeListResponse(results = emptyList())

        val result = response.toItem()

        assertTrue(result.isEmpty())
    }

    @Test
    fun `EpisodeListResponse toItem with single episode EXPECT map with one entry`() {
        val response = createEpisodeListResponse(
            results = listOf(createEpisodeResponse(episode = "S01E01"))
        )

        val result = response.toItem()

        assertEquals(1, result.size)
    }

    @Test
    fun `EpisodeListResponse toItem with single episode EXPECT correct season key`() {
        val response = createEpisodeListResponse(
            results = listOf(createEpisodeResponse(episode = "S01E01"))
        )

        val result = response.toItem()

        assertTrue(result.containsKey(1))
    }

    @Test
    fun `EpisodeListResponse toItem with multiple seasons EXPECT correct map size`() {
        val response = createEpisodeListResponse(
            results = listOf(
                createEpisodeResponse(id = 1, episode = "S01E01"),
                createEpisodeResponse(id = 12, episode = "S02E01")
            )
        )

        val result = response.toItem()

        assertEquals(2, result.size)
    }

    // PaginatorState<EpisodeListResponse>.toItem() tests

    @Test
    fun `PaginatorState Initial toItem EXPECT EpisodeState Initial`() {
        val state: PaginatorState<EpisodeListResponse> = PaginatorState.Initial

        val result = state.toItem()

        assertIs<EpisodeState.Initial>(result)
    }

    @Test
    fun `PaginatorState Loading toItem EXPECT EpisodeState Loading`() {
        val state: PaginatorState<EpisodeListResponse> = PaginatorState.Loading

        val result = state.toItem()

        assertIs<EpisodeState.Loading>(result)
    }

    @Test
    fun `PaginatorState End toItem EXPECT EpisodeState End`() {
        val state: PaginatorState<EpisodeListResponse> = PaginatorState.End

        val result = state.toItem()

        assertIs<EpisodeState.End>(result)
    }

    @Test
    fun `PaginatorState Error toItem EXPECT EpisodeState Error`() {
        val exception = RuntimeException("Test error")
        val state: PaginatorState<EpisodeListResponse> = PaginatorState.Error(exception)

        val result = state.toItem()

        assertIs<EpisodeState.Error>(result)
    }

    @Test
    fun `PaginatorState Error toItem EXPECT same throwable preserved`() {
        val exception = RuntimeException("Test error")
        val state: PaginatorState<EpisodeListResponse> = PaginatorState.Error(exception)

        val result = state.toItem() as EpisodeState.Error

        assertEquals(exception, result.throwable)
    }

    @Test
    fun `PaginatorState Error toItem EXPECT correct error message`() {
        val errorMessage = "Network connection failed"
        val exception = IllegalStateException(errorMessage)
        val state: PaginatorState<EpisodeListResponse> = PaginatorState.Error(exception)

        val result = state.toItem() as EpisodeState.Error

        assertEquals(errorMessage, result.throwable.message)
    }

    @Test
    fun `PaginatorState Success toItem EXPECT EpisodeState Success`() {
        val response = createEpisodeListResponse(
            results = listOf(createEpisodeResponse(episode = "S01E01"))
        )
        val state: PaginatorState<EpisodeListResponse> = PaginatorState.Success(response)

        val result = state.toItem()

        assertIs<EpisodeState.Success>(result)
    }

    @Test
    fun `PaginatorState Success toItem EXPECT correct seasons map`() {
        val response = createEpisodeListResponse(
            results = listOf(
                createEpisodeResponse(id = 1, episode = "S01E01"),
                createEpisodeResponse(id = 2, episode = "S01E02")
            )
        )
        val state: PaginatorState<EpisodeListResponse> = PaginatorState.Success(response)

        val result = state.toItem() as EpisodeState.Success

        assertEquals(2, result.value[1]?.size)
    }

    @Test
    fun `PaginatorState Success with empty results EXPECT EpisodeState Success with empty map`() {
        val response = createEpisodeListResponse(results = emptyList())
        val state: PaginatorState<EpisodeListResponse> = PaginatorState.Success(response)

        val result = state.toItem() as EpisodeState.Success

        assertTrue(result.value.isEmpty())
    }

    // Helper functions

    private fun createEpisodeResponse(
        id: Int = 1,
        name: String = "Test Episode",
        airDate: String = "January 1, 2020",
        episode: String = "S01E01"
    ): EpisodeResponse {
        return EpisodeResponse(
            id = id,
            name = name,
            airDate = airDate,
            episode = episode
        )
    }

    private fun createEpisodeListResponse(
        count: Int = 51,
        pages: Int = 3,
        next: String? = "https://rickandmortyapi.com/api/episode?page=2",
        prev: String? = null,
        results: List<EpisodeResponse> = emptyList()
    ): EpisodeListResponse {
        return EpisodeListResponse(
            info = InfoResponse(
                count = count,
                pages = pages,
                next = next,
                prev = prev
            ),
            results = results
        )
    }
}
