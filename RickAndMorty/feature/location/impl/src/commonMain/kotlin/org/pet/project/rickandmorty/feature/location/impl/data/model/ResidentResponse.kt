package org.pet.project.rickandmorty.feature.location.impl.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class ResidentResponse(
	@SerialName("id")
	val id: Int,

	@SerialName("name")
	val name: String,

	@SerialName("image")
	val image: String
)
