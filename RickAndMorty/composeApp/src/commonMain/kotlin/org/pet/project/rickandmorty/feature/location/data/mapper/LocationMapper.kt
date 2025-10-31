package org.pet.project.rickandmorty.feature.location.data.mapper

import org.pet.project.rickandmorty.feature.location.data.model.LocationResponse
import org.pet.project.rickandmorty.feature.location.data.model.ResidentResponse
import org.pet.project.rickandmorty.feature.location.domain.entity.Location
import org.pet.project.rickandmorty.feature.location.domain.entity.Resident

internal fun LocationResponse.toItem() =
	Location(
		id = this.id,
		name = this.name,
		type = this.type,
		dimension = this.dimension
	)

internal fun ResidentResponse.toItem(): Resident {
	return Resident(
		id = this.id,
		name = this.name,
		image = this.image
	)
}
