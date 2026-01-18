package org.pet.project.rickandmorty.feature.episode.impl.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.pet.project.rickandmorty.feature.episode.api.domain.entity.Episode

class GetEpisodesUseCase {

    suspend operator fun invoke(
        currentEpisodes: Map<Int, List<Episode>>,
        newEpisodes: Map<Int, List<Episode>>
    ) : Map<Int, List<Episode>> = withContext(Dispatchers.Default) {
        (currentEpisodes.keys + newEpisodes.keys)
            .associateWith { season ->
                (currentEpisodes[season] ?: emptyList()) + (newEpisodes[season] ?: emptyList())
            }
    }
}
