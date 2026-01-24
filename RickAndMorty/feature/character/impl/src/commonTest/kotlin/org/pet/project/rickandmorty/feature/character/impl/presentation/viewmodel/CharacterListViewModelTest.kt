package org.pet.project.rickandmorty.feature.character.impl.presentation.viewmodel

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.pet.project.rickandmorty.feature.character.impl.presentation.event.CharacterListEvent
import org.pet.project.rickandmorty.feature.character.impl.presentation.intent.CharacterListIntent
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterListViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: FakeCharacterRepository
    private lateinit var viewModel: CharacterListViewModel

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = FakeCharacterRepository()
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel(): CharacterListViewModel {
        return CharacterListViewModel(characterRepository = repository)
    }

    @Test
    fun `init EXPECT skeleton state is true`() = runTest(testDispatcher) {
        viewModel = createViewModel()

        viewModel.state.test {
            val state = awaitItem()
            assertTrue(state.skeleton)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `init EXPECT initial characters list is empty`() = runTest(testDispatcher) {
        viewModel = createViewModel()

        viewModel.state.test {
            val state = awaitItem()
            assertTrue(state.characters.isEmpty())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `init EXPECT error is false`() = runTest(testDispatcher) {
        viewModel = createViewModel()

        viewModel.state.test {
            val state = awaitItem()
            assertFalse(state.error)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `init EXPECT uploadAllCharacters is false`() = runTest(testDispatcher) {
        viewModel = createViewModel()

        viewModel.state.test {
            val state = awaitItem()
            assertFalse(state.uploadAllCharacters)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `init EXPECT uploading is false`() = runTest(testDispatcher) {
        viewModel = createViewModel()

        viewModel.state.test {
            val state = awaitItem()
            assertFalse(state.uploading)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `openCharacterScreen intent EXPECT navigation event emitted`() = runTest(testDispatcher) {
        viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.event.test {
            viewModel.onIntent(CharacterListIntent.OpenCharacterScreen(id = 42))
            advanceUntilIdle()

            val event = awaitItem()
            assertTrue(event is CharacterListEvent.OpenCharacterScreen)
        }
    }

    @Test
    fun `openCharacterScreen intent EXPECT correct id in event`() = runTest(testDispatcher) {
        viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.event.test {
            viewModel.onIntent(CharacterListIntent.OpenCharacterScreen(id = 42))
            advanceUntilIdle()

            val event = awaitItem() as CharacterListEvent.OpenCharacterScreen
            assertEquals(42, event.id)
        }
    }

    @Test
    fun `openCharacterScreen intent with zero id EXPECT event with zero`() = runTest(testDispatcher) {
        viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.event.test {
            viewModel.onIntent(CharacterListIntent.OpenCharacterScreen(id = 0))
            advanceUntilIdle()

            val event = awaitItem() as CharacterListEvent.OpenCharacterScreen
            assertEquals(0, event.id)
        }
    }

    @Test
    fun `refresh intent EXPECT state shows refreshing`() = runTest(testDispatcher) {
        viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.onIntent(CharacterListIntent.Refresh)

        viewModel.state.test {
            val state = awaitItem()
            assertTrue(state.skeleton)
        }
    }
}
