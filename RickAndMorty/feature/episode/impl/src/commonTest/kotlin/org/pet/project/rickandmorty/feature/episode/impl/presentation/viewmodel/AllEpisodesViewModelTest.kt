package org.pet.project.rickandmorty.feature.episode.impl.presentation.viewmodel

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.pet.project.rickandmorty.feature.episode.api.domain.entity.EpisodeState
import org.pet.project.rickandmorty.feature.episode.impl.domain.usecase.GetEpisodesUseCase
import org.pet.project.rickandmorty.feature.episode.impl.presentation.intent.AllEpisodesIntent
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class AllEpisodesViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: FakeEpisodeRepository
    private lateinit var getEpisodesUseCase: GetEpisodesUseCase
    private lateinit var viewModel: AllEpisodesViewModel

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = FakeEpisodeRepository()
        getEpisodesUseCase = GetEpisodesUseCase()
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel(): AllEpisodesViewModel {
        return AllEpisodesViewModel(
            episodeRepository = repository,
            getEpisodesUseCase = getEpisodesUseCase
        )
    }

    @Test
    fun `init EXPECT loading state is true`() = runTest(testDispatcher) {
        viewModel = createViewModel()

        viewModel.state.test {
            val state = awaitItem()
            assertTrue(state.loading)
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
    fun `init EXPECT uploading is false`() = runTest(testDispatcher) {
        viewModel = createViewModel()

        viewModel.state.test {
            val state = awaitItem()
            assertFalse(state.uploading)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `init EXPECT end is false`() = runTest(testDispatcher) {
        viewModel = createViewModel()

        viewModel.state.test {
            val state = awaitItem()
            assertFalse(state.end)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `init EXPECT episodes is empty`() = runTest(testDispatcher) {
        viewModel = createViewModel()

        viewModel.state.test {
            val state = awaitItem()
            assertTrue(state.episodes.isEmpty())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `Refresh intent EXPECT loading state is true`() = runTest(testDispatcher) {
        viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.onIntent(AllEpisodesIntent.Refresh)

        viewModel.state.test {
            val state = awaitItem()
            assertTrue(state.loading)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `Upload intent EXPECT uploading state is true`() = runTest(testDispatcher) {
        viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.onIntent(AllEpisodesIntent.Upload)

        viewModel.state.test {
            val state = awaitItem()
            assertTrue(state.uploading)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `loadEpisodes EXPECT repository loadEpisodes called`() = runTest(testDispatcher) {
        viewModel = createViewModel()
        advanceUntilIdle()

        assertTrue(repository.loadEpisodesCallCount >= 1)
    }

}
