package org.pet.project.rickandmorty.feature.location.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.pet.project.rickandmorty.feature.location.domain.entity.Resident

@Serializable
internal class ResidentResponse(
	@SerialName("id")
	val id: Int,

	@SerialName("name")
	val name: String,

	@SerialName("image")
	val image: String
)
