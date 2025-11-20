package org.pet.project.rickandmorty.feature.location.presentation.state

import org.pet.project.rickandmorty.common.presentation.State
import org.pet.project.rickandmorty.feature.location.domain.entity.Location
import org.pet.project.rickandmorty.feature.location.domain.entity.Resident

internal data class LocationItemState(
	val locationState: LocationItemInfoState = LocationItemInfoState(),
	val residentState: ResidentListState = ResidentListState()
) : State

internal data class LocationItemInfoState(
	val skeleton: Boolean = false,
	val error: Boolean = false,
	val location: Location? = null
)

internal data class ResidentListState(
	val skeleton: Boolean = false,
	val loadError: Boolean = false,
	val uploading: Boolean = false,
	val uploadError: Boolean = false,
	val visibleMore: Boolean = false,
	val residents: List<Resident> = emptyList(),
	val visibleResidents: List<Resident> = emptyList()
)