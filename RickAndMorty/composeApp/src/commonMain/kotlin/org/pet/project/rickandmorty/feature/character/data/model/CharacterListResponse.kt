package org.pet.project.rickandmorty.feature.character.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class CharacterListResponse(
    @SerialName("info")
    val info: CharacterListInfoResponse,
    @SerialName("results")
    val results: List<CharacterResponse>
)