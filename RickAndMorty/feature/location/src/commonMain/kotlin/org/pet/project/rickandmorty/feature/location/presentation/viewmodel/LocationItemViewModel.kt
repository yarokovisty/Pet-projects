package org.pet.project.rickandmorty.feature.location.presentation.viewmodel

import kotlinx.coroutines.delay
import org.pet.project.rickandmorty.common.presentation.BaseViewModel
import org.pet.project.rickandmorty.library.result.onFailure
import org.pet.project.rickandmorty.library.result.onSuccessAsync
import org.pet.project.rickandmorty.feature.location.domain.entity.Location
import org.pet.project.rickandmorty.feature.location.domain.entity.Resident
import org.pet.project.rickandmorty.feature.location.domain.entity.ResidentState
import org.pet.project.rickandmorty.feature.location.domain.repository.LocationRepository
import org.pet.project.rickandmorty.feature.location.domain.usecase.LoadNextResidentsUseCase
import org.pet.project.rickandmorty.feature.location.presentation.event.LocationItemEvent
import org.pet.project.rickandmorty.feature.location.presentation.intent.LocationItemIntent
import org.pet.project.rickandmorty.feature.location.presentation.state.LocationItemState

internal class LocationItemViewModel(
    private val name: String,
    private val repository: LocationRepository,
    private val loadNextResidentsUseCase: LoadNextResidentsUseCase
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
        updateState {
            copy(
                locationState = locationState.copy(skeleton = true, error = false),
                residentState = residentState.copy(skeleton = true)
            )
        }

        launchInScope {
            repository.getLocationByName(name)
                .onSuccessAsync(::handleSuccess)
                .onFailure(::handleFailure)
        }
    }

    private suspend fun handleSuccess(location: Location) {
        updateState {
            copy(
                locationState = locationState.copy(skeleton = false, location = location)
            )
        }

        repository.loadNextResidents()
    }

    private fun handleFailure(throwable: Throwable) {
        updateState {
            copy(
                locationState = locationState.copy(skeleton = false, error = true)
            )
        }
    }

    private fun loadNextResidents() {
        launchInScope {
            val state = stateValue.residentState
            val all = state.residents
            val visible = state.visibleResidents

            loadNextResidentsUseCase(visible, all)?.let { nextChunk ->
                updateState {
                    copy(residentState = residentState.copy(visibleResidents = visible + nextChunk))
                }
            }
        }
    }

    private fun observerResidents() {
        launchInScope {
            repository.residents.collect {
                when (it) {
                    is ResidentState.Error -> {

                    }

                    is ResidentState.Loading -> handleResidentUploading()

                    is ResidentState.Ended -> {

                    }

                    is ResidentState.Success -> handleResidentSuccess(it.value, it.reached)
                }
            }
        }
    }

    private fun handleResidentUploading() {
        updateState {
            copy(
                residentState = residentState.copy(uploading = true, visibleMore = false)
            )
        }
    }

    private fun handleResidentSuccess(
        residents: List<Resident>,
        reached: Boolean
    ) {
        updateState {
            val allResidents = residentState.residents + residents

            val fromIndex = residentState.visibleResidents.size
            val toIndex = if ((fromIndex + CHUNK_SIZE) >= allResidents.size) {
                allResidents.size
            } else {
                fromIndex + CHUNK_SIZE
            }
            val visibleResidents = residentState.visibleResidents + allResidents.subList(fromIndex, toIndex)

            copy(
                residentState = residentState.copy(
                    skeleton = false,
                    uploading = false,
                    visibleMore = !reached,
                    residents = allResidents,
                    visibleResidents = visibleResidents
                )
            )
        }
    }
}