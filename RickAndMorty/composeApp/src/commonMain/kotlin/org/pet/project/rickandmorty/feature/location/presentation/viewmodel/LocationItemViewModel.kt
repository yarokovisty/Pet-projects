package org.pet.project.rickandmorty.feature.location.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.pet.project.rickandmorty.common.presentation.BaseViewModel
import org.pet.project.rickandmorty.core.result.onFailure
import org.pet.project.rickandmorty.core.result.onSuccess
import org.pet.project.rickandmorty.feature.location.domain.entity.Location
import org.pet.project.rickandmorty.feature.location.domain.repository.LocationRepository
import org.pet.project.rickandmorty.feature.location.presentation.intent.LocationItemIntent
import org.pet.project.rickandmorty.feature.location.presentation.state.LocationItemState

class LocationItemViewModel(
	private val name: String,
	private val repository: LocationRepository
) : BaseViewModel<LocationItemState, LocationItemIntent, Nothing>() {

	init {
		loadLocation()
	}

	override fun initState(): LocationItemState = LocationItemState(loading = true)

	override fun onIntent(intent: LocationItemIntent) {
		TODO("Not yet implemented")
	}

	private fun loadLocation() {
		viewModelScope.launch(Dispatchers.Default) {
			repository.getLocationByName(name)
				.onSuccess(::handleSuccess)
				.onFailure(::handleFailure)
		}
	}

	private fun handleSuccess(location: Location) {
		updateState { copy(loading = false, location = location) }
	}

	private fun handleFailure(throwable: Throwable) {
		updateState { copy(loading = false, error = true) }
	}
}