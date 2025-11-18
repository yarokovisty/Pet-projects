package org.pet.project.rickandmorty.feature.location.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class LocationResponse(
    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String,

    @SerialName("type")
    val type: String,

    @SerialName("dimension")
    val dimension: String,

    @SerialName("residents")
    val residents: List<String>,

    @SerialName("url")
    val url: String,

    @SerialName("created")
    val created: String
)
