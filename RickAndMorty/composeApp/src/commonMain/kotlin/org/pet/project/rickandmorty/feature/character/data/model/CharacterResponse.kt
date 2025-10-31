package org.pet.project.rickandmorty.feature.character.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.pet.project.rickandmorty.feature.character.domain.entity.Character
import org.pet.project.rickandmorty.feature.character.domain.entity.Gender
import org.pet.project.rickandmorty.feature.character.domain.entity.Status

@Serializable
internal class CharacterResponse(
    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String,

    @SerialName("status")
    val status: String,

    @SerialName("species")
    val species: String,

    @SerialName("type")
    val type: String,

    @SerialName("gender")
    val gender: String,

    @SerialName("origin")
    val origin: Origin,

    @SerialName("location")
    val location: Location,

    @SerialName("image")
    val image: String,

    @SerialName("episode")
    val episode: List<String>,

    @SerialName("url")
    val url: String,

    @SerialName("created")
    val created: String
)

@Serializable
internal class Origin(
    @SerialName("name")
    val name: String,

    @SerialName("url")
    val url: String
)

@Serializable
internal class Location(
    @SerialName("name")
    val name: String,

    @SerialName("url")
    val url: String
)

