package org.pet.project.rickandmorty.feature.location.presentation.viewmodel

import org.pet.project.rickandmorty.common.presentation.BaseViewModel
import org.pet.project.rickandmorty.core.result.onFailure
import org.pet.project.rickandmorty.core.result.onSuccessAsync
import org.pet.project.rickandmorty.feature.location.domain.entity.Location
import org.pet.project.rickandmorty.feature.location.domain.entity.Resident
import org.pet.project.rickandmorty.feature.location.domain.entity.ResidentState
import org.pet.project.rickandmorty.feature.location.domain.repository.LocationRepository
import org.pet.project.rickandmorty.feature.location.presentation.event.LocationItemEvent
import org.pet.project.rickandmorty.feature.location.presentation.intent.LocationItemIntent
import org.pet.project.rickandmorty.feature.location.presentation.state.LocationItemState

internal class LocationItemViewModel(
    private val name: String,
    private val repository: LocationRepository
) : BaseViewModel<LocationItemState, LocationItemIntent, LocationItemEvent>() {

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

    }

    private fun observerResidents() {
        launchInScope {
            repository.residents.collect {
                when (it) {
                    is ResidentState.Error -> {

                    }

                    is ResidentState.Loading -> {

                    }

                    is ResidentState.Ended -> {

                    }

                    is ResidentState.Success -> handleResidentSuccess(it.value, it.reached)
                }
            }
        }
    }

    private fun handleResidentSuccess(
        residents: List<Resident>,
        reached: Boolean
    ) {
        updateState {
            val allResidents = residentState.residents + residents

            val fromIndex = residentState.visibleResidents.size
            val toIndex = if ((fromIndex + 4) >= allResidents.size) {
                allResidents.size
            } else {
                fromIndex + 4
            }
            val visibleResidents = residentState.visibleResidents + allResidents.subList(fromIndex, toIndex)

            copy(
                residentState = residentState.copy(
                    skeleton = false,
                    uploading = false,
                    uploadAll = reached,
                    residents = allResidents,
                    visibleResidents = visibleResidents
                )
            )
        }
    }
}