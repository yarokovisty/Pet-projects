package org.pet.project.rickandmorty.feature.location.impl.presentation.viewmodel

import org.pet.project.rickandmorty.common.presentation.BaseViewModel
import org.pet.project.rickandmorty.feature.location.api.domain.entity.Location
import org.pet.project.rickandmorty.feature.location.api.domain.entity.Resident
import org.pet.project.rickandmorty.feature.location.api.domain.entity.ResidentState
import org.pet.project.rickandmorty.feature.location.api.domain.repository.LocationRepository
import org.pet.project.rickandmorty.feature.location.impl.domain.usecase.LoadNextResidentsUseCase
import org.pet.project.rickandmorty.feature.location.impl.presentation.event.LocationItemEvent
import org.pet.project.rickandmorty.feature.location.impl.presentation.intent.LocationItemIntent
import org.pet.project.rickandmorty.feature.location.impl.presentation.state.LocationItemState
import org.pet.project.rickandmorty.feature.location.impl.presentation.state.loading
import org.pet.project.rickandmorty.feature.location.impl.presentation.state.locationFailure
import org.pet.project.rickandmorty.feature.location.impl.presentation.state.locationSuccess
import org.pet.project.rickandmorty.feature.location.impl.presentation.state.residentsEmpty
import org.pet.project.rickandmorty.feature.location.impl.presentation.state.residentsErrorUpload
import org.pet.project.rickandmorty.feature.location.impl.presentation.state.residentsFailure
import org.pet.project.rickandmorty.feature.location.impl.presentation.state.residentsFromCache
import org.pet.project.rickandmorty.feature.location.impl.presentation.state.residentsSuccess
import org.pet.project.rickandmorty.feature.location.impl.presentation.state.uploadingResidents
import org.pet.project.rickandmorty.library.result.onFailure
import org.pet.project.rickandmorty.library.result.onSuccessAsync

internal class LocationItemViewModel(
    private val name: String,
    private val repository: LocationRepository,
    private val loadNextResidentsUseCase: LoadNextResidentsUseCase,
) : BaseViewModel<LocationItemState, LocationItemIntent, LocationItemEvent>() {

    companion object {

        private const val CHUNK_SIZE = 4
    }

    init {
        observerResidents()
        loadLocation()
    }

    override fun initState(): LocationItemState = LocationItemState()

    override fun onIntent(intent: LocationItemIntent) {
        when (intent) {
            LocationItemIntent.Refresh -> loadLocation()
            LocationItemIntent.NavigateBack -> navigateBack()
            LocationItemIntent.UploadResidents -> loadNextResidents()
        }
    }

    private fun navigateBack() {
        launchInScope {
            setEvent(LocationItemEvent.NavigateBack)
        }
    }

    private fun loadLocation() {
        updateState { loading() }

        launchInScope {
            repository.getLocationByName(name)
                .onSuccessAsync(::handleSuccess)
                .onFailure(::handleFailure)
        }
    }

    private suspend fun handleSuccess(location: Location) {
        updateState { locationSuccess(location) }

        repository.loadNextResidents()
    }

    private fun handleFailure(throwable: Throwable) {
        updateState { locationFailure() }
    }

    private fun loadNextResidents() {
        launchInScope {
            val state = stateValue.residentState
            val all = state.residents
            val visible = state.visibleResidents

            loadNextResidentsUseCase(visible, all)?.let { nextChunk ->
                updateState { residentsFromCache(visible + nextChunk) }
            }
        }
    }

    private fun observerResidents() {
        launchInScope {
            repository.residents.collect {
                when (it) {
                    is ResidentState.Error   -> handleResidentError()
                    is ResidentState.Loading -> handleResidentUploading()
                    is ResidentState.Ended -> {}
                    is ResidentState.Success -> handleResidentSuccess(it.value, it.reached)
                }
            }
        }
    }

    private suspend fun handleResidentError() {
        updateState { residentsFailure() }
        setEvent(LocationItemEvent.ErrorUploadResidents)
    }

    private fun handleResidentUploading() {
        updateState { uploadingResidents() }
    }

    private suspend fun handleResidentSuccess(
        residents: List<Resident>,
        reached: Boolean,
    ) {
        when {
            residents.isNotEmpty() -> handleNonEmptyResidents(residents, reached)
            stateValue.residentState.residents.isEmpty() -> handleEmptyResidents()
            else -> handleUploadError()
        }
    }

    private fun handleNonEmptyResidents(
        residents: List<Resident>,
        reached: Boolean,
    ) = updateState {
        val allResidents = residentState.residents + residents

        val fromIndex = residentState.visibleResidents.size
        val toIndex = if ((fromIndex + CHUNK_SIZE) >= allResidents.size) {
            allResidents.size
        } else {
            fromIndex + CHUNK_SIZE
        }
        val visibleResidents =
            residentState.visibleResidents + allResidents.subList(fromIndex, toIndex)

        residentsSuccess(reached, allResidents, visibleResidents)
    }

    private suspend fun handleEmptyResidents() {
        updateState { residentsEmpty() }
        setEvent(LocationItemEvent.ErrorUploadResidents)
    }

    private suspend fun handleUploadError() {
        updateState { residentsErrorUpload() }
        setEvent(LocationItemEvent.ErrorUploadResidents)
    }
}