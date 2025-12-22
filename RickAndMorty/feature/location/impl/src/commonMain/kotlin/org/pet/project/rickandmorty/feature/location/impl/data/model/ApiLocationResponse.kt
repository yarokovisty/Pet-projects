package org.pet.project.rickandmorty.feature.location.impl.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class ApiLocationResponse(
    @SerialName("results")
    val results: List<LocationResponse>
)