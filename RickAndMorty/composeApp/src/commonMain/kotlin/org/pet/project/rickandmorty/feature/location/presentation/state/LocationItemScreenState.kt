package org.pet.project.rickandmorty.feature.location.presentation.state

import org.pet.project.rickandmorty.common.presentation.State
import org.pet.project.rickandmorty.feature.character.data.model.Location

internal data class LocationItemScreenState(
	val locationInfoState: LocationItemState = LocationItemState(),
	val residentListState: ResidentListState = ResidentListState()
) : State

internal data class LocationItemState(
	val skeleton: Boolean = false,
	val error: Boolean = false,
	val location: Location? = null
)

internal data class ResidentListState(
	val skeleton: Boolean = false,
	val error: Boolean = false
)