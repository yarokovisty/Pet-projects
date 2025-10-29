package org.pet.project.rickandmorty.feature.location.presentation.state

import org.pet.project.rickandmorty.common.presentation.State
import org.pet.project.rickandmorty.feature.location.domain.entity.Location

data class LocationItemState(
	val loading: Boolean = false,
	val error: Boolean = false,
	val location: Location? = null
) : State