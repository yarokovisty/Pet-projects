package org.pet.project.rickandmorty.feature.episode.api.domain.entity

sealed interface EpisodeState {
	object Initial : EpisodeState
	class Success(val value: Map<Int, List<Episode>>) : EpisodeState
	class Error(val throwable: Throwable) : EpisodeState
	object Loading : EpisodeState
	object End : EpisodeState
}