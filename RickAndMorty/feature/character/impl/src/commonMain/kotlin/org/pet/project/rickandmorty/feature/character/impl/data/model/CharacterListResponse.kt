package org.pet.project.rickandmorty.feature.character.impl.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.pet.project.rickandmorty.common.data.InfoResponse

@Serializable
internal class CharacterListResponse(
    @SerialName("info")
    val info: InfoResponse,
    @SerialName("results")
    val results: List<CharacterResponse>
)