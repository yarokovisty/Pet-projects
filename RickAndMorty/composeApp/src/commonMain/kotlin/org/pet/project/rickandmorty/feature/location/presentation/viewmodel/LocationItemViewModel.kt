package org.pet.project.rickandmorty.feature.location.presentation.viewmodel

import org.pet.project.rickandmorty.common.presentation.BaseViewModel
import org.pet.project.rickandmorty.core.result.onFailure
import org.pet.project.rickandmorty.core.result.onSuccess
import org.pet.project.rickandmorty.feature.location.domain.entity.Location
import org.pet.project.rickandmorty.feature.location.domain.repository.LocationRepository
import org.pet.project.rickandmorty.feature.location.presentation.event.LocationItemEvent
import org.pet.project.rickandmorty.feature.location.presentation.intent.LocationItemIntent
import org.pet.project.rickandmorty.feature.location.presentation.state.LocationItemState

internal class LocationItemViewModel(
    private val name: String,
    private val repository: LocationRepository
) : BaseViewModel<LocationItemState, LocationItemIntent, LocationItemEvent>() {

    init {
        loadLocation()
        observerResidents()
    }

    override fun initState(): LocationItemState = LocationItemState()

    override fun onIntent(intent: LocationItemIntent) {
        when (intent) {
            LocationItemIntent.Refresh -> loadLocation()
            LocationItemIntent.NavigateBack -> navigateBack()
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
                locationState = locationState.copy(skeleton = true, error = false)
            )
        }

        launchInScope {
            repository.getLocationByName(name)
                .onSuccess(::handleSuccess)
                .onFailure(::handleFailure)
        }
    }

    private fun handleSuccess(location: Location) {
        updateState {
            copy(
                locationState = locationState.copy(skeleton = false, location = location)
            )
        }
    }

    private fun handleFailure(throwable: Throwable) {
        updateState {
            copy(
                locationState = locationState.copy(skeleton = false, error = true)
            )
        }
    }

    private fun observerResidents() {
        launchInScope {
            repository.residents.collect {

            }
        }
    }
}