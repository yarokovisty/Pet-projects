package org.pet.project.rickandmorty.feature.episode.impl.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.pet.project.rickandmorty.common.data.InfoResponse

@Serializable
internal class EpisodeListResponse(
	@SerialName("info")
	val info: InfoResponse,

	@SerialName("results")
	val results: List<EpisodeResponse>
)