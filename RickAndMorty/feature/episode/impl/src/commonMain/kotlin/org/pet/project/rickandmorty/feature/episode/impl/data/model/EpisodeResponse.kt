package org.pet.project.rickandmorty.feature.episode.impl.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class EpisodeResponse(
    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String,

    @SerialName("air_date")
    val airDate: String,

    @SerialName("episode")
    val episode: String
)