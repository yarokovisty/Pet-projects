package org.pet.project.rickandmorty.feature.episode.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.pet.project.rickandmorty.feature.episode.domain.entity.Episode

internal class GetEpisodesUseCase {

    suspend operator fun invoke(
        currentEpisodes: Map<Int, List<Episode>>,
        newEpisodes: Map<Int, List<Episode>>
    ) : Map<Int, List<Episode>> = withContext(Dispatchers.Default) {
        (newEpisodes.keys + currentEpisodes.keys).associateWith  { key ->
            (currentEpisodes[key] ?: emptyList()) + (newEpisodes[key] ?: emptyList())
        }
    }
}