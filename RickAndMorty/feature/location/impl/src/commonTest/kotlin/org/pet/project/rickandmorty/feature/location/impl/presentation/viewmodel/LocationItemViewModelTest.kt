package org.pet.project.rickandmorty.feature.location.impl.presentation.viewmodel

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.pet.project.rickandmorty.feature.location.api.domain.entity.ResidentState
import org.pet.project.rickandmorty.feature.location.impl.domain.usecase.LoadNextResidentsUseCase
import org.pet.project.rickandmorty.feature.location.impl.presentation.event.LocationItemEvent
import org.pet.project.rickandmorty.feature.location.impl.presentation.intent.LocationItemIntent
import org.pet.project.rickandmorty.library.result.Result
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class LocationItemViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: FakeLocationRepository
    private lateinit var loadNextResidentsUseCase: LoadNextResidentsUseCase
    private lateinit var viewModel: LocationItemViewModel

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = FakeLocationRepository()
        loadNextResidentsUseCase = LoadNextResidentsUseCase(repository)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel(name: String = "Earth (C-137)"): LocationItemViewModel {
        return LocationItemViewModel(
            name = name,
            repository = repository,
            loadNextResidentsUseCase = loadNextResidentsUseCase
        )
    }

    @Test
    fun `init EXPECT error is false by default`() = runTest(testDispatcher) {
        viewModel = createViewModel(name = "Earth (C-137)")

        viewModel.state.test {
            val state = awaitItem()
            assertFalse(state.locationState.error)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `init with success result EXPECT location is loaded correctly`() = runTest(testDispatcher) {
        val testLocation = FakeLocationRepository.createTestLocation(
            id = 1,
            name = "Earth (C-137)",
            type = "Planet",
            dimension = "Dimension C-137",
            amountResidents = 10
        )
        repository.getLocationByNameResult = Result.Success.Value(testLocation)
        viewModel = createViewModel(name = "Earth (C-137)")
        advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertEquals(testLocation, state.locationState.location)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `init with failure result EXPECT error state is set`() = runTest(testDispatcher) {
        repository.getLocationByNameResult = Result.Failure.Error(Exception("Network error"))
        viewModel = createViewModel(name = "Earth (C-137)")
        advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertTrue(state.locationState.error)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `NavigateBack intent EXPECT NavigateBack event is emitted`() = runTest(testDispatcher) {
        viewModel = createViewModel(name = "Earth (C-137)")
        advanceUntilIdle()

        viewModel.event.test {
            viewModel.onIntent(LocationItemIntent.NavigateBack)
            advanceUntilIdle()

            val event = awaitItem()
            assertEquals(LocationItemEvent.NavigateBack, event)
        }
    }

    @Test
    fun `Refresh intent EXPECT skeleton is true during loading`() = runTest(testDispatcher) {
        viewModel = createViewModel(name = "Earth (C-137)")
        advanceUntilIdle()

        viewModel.state.test {
            awaitItem()
            viewModel.onIntent(LocationItemIntent.Refresh)

            val loadingState = awaitItem()
            assertTrue(loadingState.locationState.skeleton)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `UploadResidents intent EXPECT visibleResidents is updated`() = runTest(testDispatcher) {
        val allResidents = FakeLocationRepository.createTestResidentList(count = 8)
        viewModel = createViewModel(name = "Earth (C-137)")
        advanceUntilIdle()

        repository.emitResidentState(ResidentState.Success(value = allResidents, reached = false))
        advanceUntilIdle()

        viewModel.state.test {
            val initialState = awaitItem()
            val initialVisibleCount = initialState.residentState.visibleResidents.size

            viewModel.onIntent(LocationItemIntent.UploadResidents)
            advanceUntilIdle()

            val updatedState = awaitItem()
            assertTrue(updatedState.residentState.visibleResidents.size > initialVisibleCount)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `Residents flow Loading state EXPECT uploading is true`() = runTest(testDispatcher) {
        viewModel = createViewModel(name = "Earth (C-137)")
        advanceUntilIdle()

        repository.emitResidentState(ResidentState.Loading)
        advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertTrue(state.residentState.uploading)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `Residents flow Error state EXPECT loadError is true`() = runTest(testDispatcher) {
        viewModel = createViewModel(name = "Earth (C-137)")
        advanceUntilIdle()

        repository.emitResidentState(ResidentState.Error)
        advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertTrue(state.residentState.loadError)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `Residents flow Error state EXPECT ErrorUploadResidents event is emitted`() = runTest(testDispatcher) {
        viewModel = createViewModel(name = "Earth (C-137)")
        advanceUntilIdle()

        viewModel.event.test {
            repository.emitResidentState(ResidentState.Error)
            advanceUntilIdle()

            val event = awaitItem()
            assertEquals(LocationItemEvent.ErrorUploadResidents, event)
        }
    }

    @Test
    fun `Residents flow Success with non-empty residents EXPECT state is updated correctly`() = runTest(testDispatcher) {
        val residents = FakeLocationRepository.createTestResidentList(count = 5)
        viewModel = createViewModel(name = "Earth (C-137)")
        advanceUntilIdle()

        viewModel.state.test {
            awaitItem()
            repository.emitResidentState(ResidentState.Success(value = residents, reached = false))
            advanceUntilIdle()

            val state = awaitItem()
            assertEquals(5, state.residentState.residents.size)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `Residents flow Success with empty residents when all residents empty EXPECT loadError is true`() = runTest(testDispatcher) {
        viewModel = createViewModel(name = "Earth (C-137)")
        advanceUntilIdle()

        repository.emitResidentState(ResidentState.Success(value = emptyList(), reached = false))
        advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertTrue(state.residentState.loadError)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `Residents flow Success with empty residents when all residents empty EXPECT ErrorUploadResidents event is emitted`() = runTest(testDispatcher) {
        viewModel = createViewModel(name = "Earth (C-137)")
        advanceUntilIdle()

        viewModel.event.test {
            repository.emitResidentState(ResidentState.Success(value = emptyList(), reached = false))
            advanceUntilIdle()

            val event = awaitItem()
            assertEquals(LocationItemEvent.ErrorUploadResidents, event)
        }
    }

    @Test
    fun `Residents flow Success with empty residents when all residents not empty EXPECT uploadError is true`() = runTest(testDispatcher) {
        val initialResidents = FakeLocationRepository.createTestResidentList(count = 3)
        viewModel = createViewModel(name = "Earth (C-137)")
        advanceUntilIdle()

        viewModel.state.test {
            awaitItem()
            repository.emitResidentState(ResidentState.Success(value = initialResidents, reached = false))
            advanceUntilIdle()

            awaitItem()
            repository.emitResidentState(ResidentState.Success(value = emptyList(), reached = false))
            advanceUntilIdle()

            val state = awaitItem()
            assertTrue(state.residentState.uploadError)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `Residents flow Success with empty residents when all residents not empty EXPECT ErrorUploadResidents event is emitted`() = runTest(testDispatcher) {
        val initialResidents = FakeLocationRepository.createTestResidentList(count = 3)
        viewModel = createViewModel(name = "Earth (C-137)")
        advanceUntilIdle()

        repository.emitResidentState(ResidentState.Success(value = initialResidents, reached = false))
        advanceUntilIdle()

        viewModel.event.test {
            repository.emitResidentState(ResidentState.Success(value = emptyList(), reached = false))
            advanceUntilIdle()

            val event = awaitItem()
            assertEquals(LocationItemEvent.ErrorUploadResidents, event)
        }
    }
}
