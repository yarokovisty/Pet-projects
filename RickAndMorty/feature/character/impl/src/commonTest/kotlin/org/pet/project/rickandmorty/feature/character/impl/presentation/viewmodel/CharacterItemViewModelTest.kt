package org.pet.project.rickandmorty.feature.character.impl.presentation.viewmodel

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.pet.project.rickandmorty.feature.character.impl.presentation.event.CharacterItemEvent
import org.pet.project.rickandmorty.feature.character.impl.presentation.intent.CharacterItemIntent
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterItemViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: FakeCharacterRepository
    private lateinit var viewModel: CharacterItemViewModel

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = FakeCharacterRepository()
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel(id: Int = 1): CharacterItemViewModel {
        return CharacterItemViewModel(id = id, characterRepository = repository)
    }

    @Test
    fun `init EXPECT error is false by default`() = runTest(testDispatcher) {
        viewModel = createViewModel(id = 1)

        viewModel.state.test {
            val state = awaitItem()
            assertFalse(state.error)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `init with success result EXPECT character is not null`() = runTest(testDispatcher) {
        val character = FakeCharacterRepository.createTestCharacter(id = 1)
        repository.getCharacterResult = org.pet.project.rickandmorty.library.result.Result.Success.Value(character)

        viewModel = createViewModel(id = 1)
        advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertEquals(character, state.character)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `back intent EXPECT back event emitted`() = runTest(testDispatcher) {
        viewModel = createViewModel(id = 1)
        advanceUntilIdle()

        viewModel.event.test {
            viewModel.onIntent(CharacterItemIntent.Back)
            advanceUntilIdle()

            val event = awaitItem()
            assertEquals(CharacterItemEvent.Back, event)
        }
    }

    @Test
    fun `openOriginScreen intent EXPECT location event emitted`() = runTest(testDispatcher) {
        viewModel = createViewModel(id = 1)
        advanceUntilIdle()

        viewModel.event.test {
            viewModel.onIntent(CharacterItemIntent.OpenOriginScreen("Earth"))
            advanceUntilIdle()

            val event = awaitItem()
            assertTrue(event is CharacterItemEvent.OpenLocationScreen)
        }
    }

    @Test
    fun `openOriginScreen intent EXPECT correct location name`() = runTest(testDispatcher) {
        viewModel = createViewModel(id = 1)
        advanceUntilIdle()

        viewModel.event.test {
            viewModel.onIntent(CharacterItemIntent.OpenOriginScreen("Earth"))
            advanceUntilIdle()

            val event = awaitItem() as CharacterItemEvent.OpenLocationScreen
            assertEquals("Earth", event.name)
        }
    }

    @Test
    fun `openLocationScreen intent EXPECT location event emitted`() = runTest(testDispatcher) {
        viewModel = createViewModel(id = 1)
        advanceUntilIdle()

        viewModel.event.test {
            viewModel.onIntent(CharacterItemIntent.OpenLocationScreen("Citadel"))
            advanceUntilIdle()

            val event = awaitItem()
            assertTrue(event is CharacterItemEvent.OpenLocationScreen)
        }
    }

    @Test
    fun `openLocationScreen intent EXPECT correct location name`() = runTest(testDispatcher) {
        viewModel = createViewModel(id = 1)
        advanceUntilIdle()

        viewModel.event.test {
            viewModel.onIntent(CharacterItemIntent.OpenLocationScreen("Citadel"))
            advanceUntilIdle()

            val event = awaitItem() as CharacterItemEvent.OpenLocationScreen
            assertEquals("Citadel", event.name)
        }
    }

    @Test
    fun `openOriginScreen intent with empty name EXPECT event with empty name`() = runTest(testDispatcher) {
        viewModel = createViewModel(id = 1)
        advanceUntilIdle()

        viewModel.event.test {
            viewModel.onIntent(CharacterItemIntent.OpenOriginScreen(""))
            advanceUntilIdle()

            val event = awaitItem() as CharacterItemEvent.OpenLocationScreen
            assertEquals("", event.name)
        }
    }

    @Test
    fun `openLocationScreen intent with special characters EXPECT event with special characters`() = runTest(testDispatcher) {
        viewModel = createViewModel(id = 1)
        advanceUntilIdle()

        viewModel.event.test {
            viewModel.onIntent(CharacterItemIntent.OpenLocationScreen("Earth (C-137)"))
            advanceUntilIdle()

            val event = awaitItem() as CharacterItemEvent.OpenLocationScreen
            assertEquals("Earth (C-137)", event.name)
        }
    }
}
