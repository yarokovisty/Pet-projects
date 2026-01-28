package org.pet.project.rickandmorty.feature.episode.impl.presentation.viewmodel

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.pet.project.rickandmorty.feature.episode.api.domain.entity.Episode
import org.pet.project.rickandmorty.feature.episode.api.domain.entity.Season
import org.pet.project.rickandmorty.feature.episode.impl.presentation.event.CharacterEpisodeEvent
import org.pet.project.rickandmorty.feature.episode.impl.presentation.intent.CharacterEpisodeIntent
import org.pet.project.rickandmorty.library.result.Result
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterEpisodeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var characterRepository: FakeCharacterRepository
    private lateinit var episodeRepository: FakeEpisodeRepository
    private lateinit var viewModel: CharacterEpisodeViewModel

    private val characterId = 1

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        characterRepository = FakeCharacterRepository()
        episodeRepository = FakeEpisodeRepository()
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel(): CharacterEpisodeViewModel {
        return CharacterEpisodeViewModel(
            characterId = characterId,
            characterRepository = characterRepository,
            episodeRepository = episodeRepository
        )
    }

    // region Successful Loading Tests

    @Test
    fun `loadCharacter success EXPECT character name is updated`() = runTest(testDispatcher) {
        val testCharacter = FakeCharacterRepository.createTestCharacter(name = "Morty Smith")
        characterRepository.getCharacterResult = Result.Success.Value(testCharacter)

        viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertEquals("Morty Smith", state.character.name)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `loadCharacter success EXPECT character image is updated`() = runTest(testDispatcher) {
        val testImage = "https://example.com/image.png"
        val testCharacter = FakeCharacterRepository.createTestCharacter(image = testImage)
        characterRepository.getCharacterResult = Result.Success.Value(testCharacter)

        viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertEquals(testImage, state.character.image)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `loadEpisodes success EXPECT seasons is updated`() = runTest(testDispatcher) {
        val testSeasons = listOf(
            Season(
                num = 1,
                episodes = listOf(
                    Episode(id = 1, name = "Pilot", season = 1, seria = 1, airDate = "December 2, 2013")
                ),
                amountEpisodes = 1
            )
        )
        episodeRepository.getEpisodesResult = Result.Success.Value(testSeasons)

        viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertEquals(1, state.seasons.size)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `loadEpisodes success EXPECT loading is false`() = runTest(testDispatcher) {
        viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertFalse(state.loading)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `loadEpisodes success EXPECT error is false`() = runTest(testDispatcher) {
        viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertFalse(state.error)
            cancelAndConsumeRemainingEvents()
        }
    }

    // endregion

    // region Character Loading Failure Tests

    @Test
    fun `loadCharacter failure EXPECT error is true`() = runTest(testDispatcher) {
        characterRepository.getCharacterResult = Result.Failure.Error(Exception("Network error"))

        viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertTrue(state.error)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `loadCharacter failure EXPECT loading is false`() = runTest(testDispatcher) {
        characterRepository.getCharacterResult = Result.Failure.Error(Exception("Network error"))

        viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertFalse(state.loading)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `loadCharacter failure EXPECT character name is empty`() = runTest(testDispatcher) {
        characterRepository.getCharacterResult = Result.Failure.Error(Exception("Network error"))

        viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertTrue(state.character.name.isEmpty())
            cancelAndConsumeRemainingEvents()
        }
    }

    // endregion

    // region Refresh Intent Tests

    @Test
    fun `Refresh intent after failure EXPECT error cleared`() = runTest(testDispatcher) {
        characterRepository.getCharacterResult = Result.Failure.Error(Exception("Network error"))
        viewModel = createViewModel()
        advanceUntilIdle()

        // Reset to success
        characterRepository.getCharacterResult = Result.Success.Value(FakeCharacterRepository.createTestCharacter())

        viewModel.onIntent(CharacterEpisodeIntent.Refresh)
        advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertFalse(state.error)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `Refresh intent EXPECT updated character data`() = runTest(testDispatcher) {
        val initialCharacter = FakeCharacterRepository.createTestCharacter(name = "Rick Sanchez")
        characterRepository.getCharacterResult = Result.Success.Value(initialCharacter)

        viewModel = createViewModel()
        advanceUntilIdle()

        // Change the character for refresh
        val updatedCharacter = FakeCharacterRepository.createTestCharacter(name = "Morty Smith")
        characterRepository.getCharacterResult = Result.Success.Value(updatedCharacter)

        viewModel.onIntent(CharacterEpisodeIntent.Refresh)
        advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertEquals("Morty Smith", state.character.name)
            cancelAndConsumeRemainingEvents()
        }
    }

    // endregion

    // region Back Intent Tests

    @Test
    fun `Back intent EXPECT Back event emitted`() = runTest(testDispatcher) {
        viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.event.test {
            viewModel.onIntent(CharacterEpisodeIntent.Back)
            advanceUntilIdle()
            val event = awaitItem()
            assertIs<CharacterEpisodeEvent.Back>(event)
        }
    }

    // endregion

    // region Edge Cases Tests

    @Test
    fun `character with empty episodes list EXPECT no episodes loaded`() = runTest(testDispatcher) {
        val characterWithNoEpisodes = FakeCharacterRepository.createTestCharacter(episodes = emptyList())
        characterRepository.getCharacterResult = Result.Success.Value(characterWithNoEpisodes)
        episodeRepository.getEpisodesResult = Result.Success.Value(emptyList())

        viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertTrue(state.seasons.isEmpty())
            cancelAndConsumeRemainingEvents()
        }
    }
}
