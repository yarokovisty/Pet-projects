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
import org.pet.project.rickandmorty.feature.location.presentation.state.LocationItemScreenState
import org.pet.project.rickandmorty.utils.PlatformLogger

internal class LocationItemViewModel(
	private val name: String,
	private val repository: LocationRepository
) : BaseViewModel<LocationItemScreenState, LocationItemIntent, Nothing>() {

	init {
		loadLocation()
	}

	override fun initState() = LocationItemScreenState()

	override fun onIntent(intent: LocationItemIntent) {
		when(intent) {
			LocationItemIntent.Refresh -> loadLocation()
		}
	}

	private fun loadLocation() {

	}

	private fun handleSuccess(location: Location) {

	}

	private fun handleFailure(throwable: Throwable) {

	}
}