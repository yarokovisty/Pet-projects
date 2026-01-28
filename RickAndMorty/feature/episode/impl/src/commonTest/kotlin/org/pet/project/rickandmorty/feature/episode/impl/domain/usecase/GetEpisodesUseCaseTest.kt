package org.pet.project.rickandmorty.feature.episode.impl.domain.usecase

import kotlinx.coroutines.test.runTest
import org.pet.project.rickandmorty.feature.episode.api.domain.entity.Episode
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetEpisodesUseCaseTest {

    private lateinit var useCase: GetEpisodesUseCase

    @BeforeTest
    fun setUp() {
        useCase = GetEpisodesUseCase()
    }

    @Test
    fun `invoke with both maps empty EXPECT empty map`() = runTest {
        val currentEpisodes = emptyMap<Int, List<Episode>>()
        val newEpisodes = emptyMap<Int, List<Episode>>()

        val result = useCase(currentEpisodes, newEpisodes)

        assertTrue(result.isEmpty())
    }

    @Test
    fun `invoke with current episodes empty EXPECT new episodes returned`() = runTest {
        val currentEpisodes = emptyMap<Int, List<Episode>>()
        val newEpisodes = mapOf(
            1 to listOf(createEpisode(1, 1, 1), createEpisode(2, 1, 2))
        )

        val result = useCase(currentEpisodes, newEpisodes)

        assertEquals(2, result[1]?.size)
    }

    @Test
    fun `invoke with new episodes empty EXPECT current episodes returned`() = runTest {
        val currentEpisodes = mapOf(
            1 to listOf(createEpisode(1, 1, 1), createEpisode(2, 1, 2))
        )
        val newEpisodes = emptyMap<Int, List<Episode>>()

        val result = useCase(currentEpisodes, newEpisodes)

        assertEquals(2, result[1]?.size)
    }

    @Test
    fun `invoke with same season EXPECT episodes merged`() = runTest {
        val currentEpisodes = mapOf(
            1 to listOf(createEpisode(1, 1, 1))
        )
        val newEpisodes = mapOf(
            1 to listOf(createEpisode(2, 1, 2))
        )

        val result = useCase(currentEpisodes, newEpisodes)

        assertEquals(2, result[1]?.size)
    }

    @Test
    fun `invoke with different seasons EXPECT all seasons present`() = runTest {
        val currentEpisodes = mapOf(
            1 to listOf(createEpisode(1, 1, 1))
        )
        val newEpisodes = mapOf(
            2 to listOf(createEpisode(12, 2, 1))
        )

        val result = useCase(currentEpisodes, newEpisodes)

        assertEquals(2, result.size)
    }

    @Test
    fun `invoke with different seasons EXPECT season 1 present`() = runTest {
        val currentEpisodes = mapOf(
            1 to listOf(createEpisode(1, 1, 1))
        )
        val newEpisodes = mapOf(
            2 to listOf(createEpisode(12, 2, 1))
        )

        val result = useCase(currentEpisodes, newEpisodes)

        assertTrue(result.containsKey(1))
    }

    @Test
    fun `invoke with different seasons EXPECT season 2 present`() = runTest {
        val currentEpisodes = mapOf(
            1 to listOf(createEpisode(1, 1, 1))
        )
        val newEpisodes = mapOf(
            2 to listOf(createEpisode(12, 2, 1))
        )

        val result = useCase(currentEpisodes, newEpisodes)

        assertTrue(result.containsKey(2))
    }

    @Test
    fun `invoke with multiple seasons in both maps EXPECT correct season count`() = runTest {
        val currentEpisodes = mapOf(
            1 to listOf(createEpisode(1, 1, 1)),
            2 to listOf(createEpisode(12, 2, 1))
        )
        val newEpisodes = mapOf(
            1 to listOf(createEpisode(2, 1, 2)),
            3 to listOf(createEpisode(23, 3, 1))
        )

        val result = useCase(currentEpisodes, newEpisodes)

        assertEquals(3, result.size)
    }

    @Test
    fun `invoke with multiple seasons EXPECT season 1 has merged episodes`() = runTest {
        val currentEpisodes = mapOf(
            1 to listOf(createEpisode(1, 1, 1)),
            2 to listOf(createEpisode(12, 2, 1))
        )
        val newEpisodes = mapOf(
            1 to listOf(createEpisode(2, 1, 2)),
            3 to listOf(createEpisode(23, 3, 1))
        )

        val result = useCase(currentEpisodes, newEpisodes)

        assertEquals(2, result[1]?.size)
    }

    @Test
    fun `invoke EXPECT current episodes come before new episodes`() = runTest {
        val episode1 = createEpisode(1, 1, 1)
        val episode2 = createEpisode(2, 1, 2)
        val currentEpisodes = mapOf(1 to listOf(episode1))
        val newEpisodes = mapOf(1 to listOf(episode2))

        val result = useCase(currentEpisodes, newEpisodes)

        assertEquals(episode1.id, result[1]?.first()?.id)
    }

    @Test
    fun `invoke EXPECT new episodes come after current episodes`() = runTest {
        val episode1 = createEpisode(1, 1, 1)
        val episode2 = createEpisode(2, 1, 2)
        val currentEpisodes = mapOf(1 to listOf(episode1))
        val newEpisodes = mapOf(1 to listOf(episode2))

        val result = useCase(currentEpisodes, newEpisodes)

        assertEquals(episode2.id, result[1]?.last()?.id)
    }

    @Test
    fun `invoke with single episode each EXPECT both episodes in result`() = runTest {
        val episode1 = createEpisode(1, 1, 1)
        val episode2 = createEpisode(2, 1, 2)
        val currentEpisodes = mapOf(1 to listOf(episode1))
        val newEpisodes = mapOf(1 to listOf(episode2))

        val result = useCase(currentEpisodes, newEpisodes)

        assertEquals(2, result[1]?.size)
    }

    @Test
    fun `invoke with three seasons EXPECT all three seasons in result`() = runTest {
        val currentEpisodes = mapOf(
            1 to listOf(createEpisode(1, 1, 1)),
            2 to listOf(createEpisode(12, 2, 1)),
            3 to listOf(createEpisode(23, 3, 1))
        )
        val newEpisodes = emptyMap<Int, List<Episode>>()

        val result = useCase(currentEpisodes, newEpisodes)

        assertEquals(3, result.size)
    }

    @Test
    fun `invoke with empty list for season EXPECT empty list preserved`() = runTest {
        val currentEpisodes = mapOf(1 to emptyList<Episode>())
        val newEpisodes = mapOf(1 to emptyList<Episode>())

        val result = useCase(currentEpisodes, newEpisodes)

        assertTrue(result[1]?.isEmpty() == true)
    }

    private fun createEpisode(id: Int, season: Int, seria: Int): Episode {
        return Episode(
            id = id,
            name = "Episode $id",
            season = season,
            seria = seria,
            airDate = "January 1, 2020"
        )
    }
}
