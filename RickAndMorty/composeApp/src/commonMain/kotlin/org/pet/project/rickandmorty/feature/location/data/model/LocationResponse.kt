package org.pet.project.rickandmorty.feature.location.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.pet.project.rickandmorty.feature.character.domain.entity.Character
import org.pet.project.rickandmorty.feature.location.domain.entity.Location

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
    val characters: List<String>,

    @SerialName("url")
    val url: String,

    @SerialName("created")
    val created: String
)

internal fun LocationResponse.toItem(characters: List<Character>) =
    Location(
        id = this.id,
        name = this.name,
        type = this.type,
        dimension = this.dimension,
        characters = characters
    )
