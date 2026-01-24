package org.pet.project.rickandmorty.feature.character.impl.presentation.viewmodel

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.pet.project.rickandmorty.feature.character.impl.domain.usecase.FilterCharacterUseCase
import org.pet.project.rickandmorty.feature.character.impl.domain.usecase.GetCountCharacterByFilterUseCase
import org.pet.project.rickandmorty.feature.character.impl.presentation.event.CharacterSearchEvent
import org.pet.project.rickandmorty.feature.character.impl.presentation.intent.CharacterSearchIntent
import org.pet.project.rickandmorty.feature.character.impl.presentation.state.CharacterSearchState
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterSearchViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: FakeCharacterRepository
    private lateinit var getCountUseCase: GetCountCharacterByFilterUseCase
    private lateinit var filterUseCase: FilterCharacterUseCase
    private lateinit var viewModel: CharacterSearchViewModel

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = FakeCharacterRepository()
        getCountUseCase = GetCountCharacterByFilterUseCase()
        filterUseCase = FilterCharacterUseCase(testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel(): CharacterSearchViewModel {
        return CharacterSearchViewModel(
            characterRepository = repository,
            getCountCharacterByFilterUseCase = getCountUseCase,
            filterCharacterUseCase = filterUseCase
        )
    }

    @Test
    fun `init EXPECT initial state`() = runTest(testDispatcher) {
        viewModel = createViewModel()

        viewModel.state.test {
            val state = awaitItem()
            assertEquals(CharacterSearchState.INITIAL, state)
        }
    }

    @Test
    fun `search intent EXPECT query updated in state`() = runTest(testDispatcher) {
        viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.onIntent(CharacterSearchIntent.Search("Rick"))
        advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertEquals("Rick", state.searchInputState.query)
        }
    }

    @Test
    fun `clear intent EXPECT state reset to initial query`() = runTest(testDispatcher) {
        viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.onIntent(CharacterSearchIntent.Search("Rick"))
        advanceUntilIdle()

        viewModel.onIntent(CharacterSearchIntent.Clear)
        advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertEquals("", state.searchInputState.query)
        }
    }

    @Test
    fun `openCharacter intent EXPECT navigation event emitted`() = runTest(testDispatcher) {
        viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.event.test {
            viewModel.onIntent(CharacterSearchIntent.OpenCharacter(characterId = 99))
            advanceUntilIdle()

            val event = awaitItem()
            assertTrue(event is CharacterSearchEvent.OpenCharacterScreen)
        }
    }

    @Test
    fun `openCharacter intent EXPECT correct characterId in event`() = runTest(testDispatcher) {
        viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.event.test {
            viewModel.onIntent(CharacterSearchIntent.OpenCharacter(characterId = 99))
            advanceUntilIdle()

            val event = awaitItem() as CharacterSearchEvent.OpenCharacterScreen
            assertEquals(99, event.characterId)
        }
    }

    @Test
    fun `search intent EXPECT clearShow is true when query is not empty`() = runTest(testDispatcher) {
        viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.onIntent(CharacterSearchIntent.Search("Rick"))
        advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertTrue(state.searchInputState.clearShow)
        }
    }

    @Test
    fun `clear intent EXPECT clearShow is false`() = runTest(testDispatcher) {
        viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.onIntent(CharacterSearchIntent.Search("Rick"))
        advanceUntilIdle()

        viewModel.onIntent(CharacterSearchIntent.Clear)
        advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertFalse(state.searchInputState.clearShow)
        }
    }

    @Test
    fun `init EXPECT filterMenuState expanded is false`() = runTest(testDispatcher) {
        viewModel = createViewModel()

        viewModel.state.test {
            val state = awaitItem()
            assertFalse(state.filterMenuState.expanded)
        }
    }

    @Test
    fun `init EXPECT filterMenuState showMenuIcon is false`() = runTest(testDispatcher) {
        viewModel = createViewModel()

        viewModel.state.test {
            val state = awaitItem()
            assertFalse(state.filterMenuState.showMenuIcon)
        }
    }

    @Test
    fun `init EXPECT searchResultState loading is false`() = runTest(testDispatcher) {
        viewModel = createViewModel()

        viewModel.state.test {
            val state = awaitItem()
            assertFalse(state.searchResultState.loading)
        }
    }

    @Test
    fun `init EXPECT searchResultState error is false`() = runTest(testDispatcher) {
        viewModel = createViewModel()

        viewModel.state.test {
            val state = awaitItem()
            assertFalse(state.searchResultState.error)
        }
    }

    @Test
    fun `init EXPECT searchResultState notFound is false`() = runTest(testDispatcher) {
        viewModel = createViewModel()

        viewModel.state.test {
            val state = awaitItem()
            assertFalse(state.searchResultState.notFound)
        }
    }

    @Test
    fun `init EXPECT searchInputState query is empty`() = runTest(testDispatcher) {
        viewModel = createViewModel()

        viewModel.state.test {
            val state = awaitItem()
            assertEquals("", state.searchInputState.query)
        }
    }

    @Test
    fun `search intent with empty query EXPECT query updated`() = runTest(testDispatcher) {
        viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.onIntent(CharacterSearchIntent.Search(""))
        advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertEquals("", state.searchInputState.query)
        }
    }

    @Test
    fun `search intent with spaces EXPECT query updated with spaces`() = runTest(testDispatcher) {
        viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.onIntent(CharacterSearchIntent.Search("   "))
        advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertEquals("   ", state.searchInputState.query)
        }
    }

    @Test
    fun `multiple search intents EXPECT last query preserved`() = runTest(testDispatcher) {
        viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.onIntent(CharacterSearchIntent.Search("R"))
        viewModel.onIntent(CharacterSearchIntent.Search("Ri"))
        viewModel.onIntent(CharacterSearchIntent.Search("Rick"))
        advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertEquals("Rick", state.searchInputState.query)
        }
    }

    @Test
    fun `openCharacter intent with zero id EXPECT event emitted with zero`() = runTest(testDispatcher) {
        viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.event.test {
            viewModel.onIntent(CharacterSearchIntent.OpenCharacter(characterId = 0))
            advanceUntilIdle()

            val event = awaitItem() as CharacterSearchEvent.OpenCharacterScreen
            assertEquals(0, event.characterId)
        }
    }

    @Test
    fun `openCharacter intent with negative id EXPECT event emitted with negative id`() = runTest(testDispatcher) {
        viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.event.test {
            viewModel.onIntent(CharacterSearchIntent.OpenCharacter(characterId = -1))
            advanceUntilIdle()

            val event = awaitItem() as CharacterSearchEvent.OpenCharacterScreen
            assertEquals(-1, event.characterId)
        }
    }
}
