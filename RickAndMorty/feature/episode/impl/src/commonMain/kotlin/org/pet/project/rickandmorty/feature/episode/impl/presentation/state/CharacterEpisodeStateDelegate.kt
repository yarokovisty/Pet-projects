package org.pet.project.rickandmorty.feature.episode.impl.presentation.state

import org.pet.project.rickandmorty.feature.episode.api.domain.entity.Season

internal fun CharacterEpisodeState.loading(): CharacterEpisodeState {
    return copy(loading = true, error = false)
}

internal fun CharacterEpisodeState.characterSuccess(
    name: String,
    image: String
): CharacterEpisodeState {
    return copy(character = CharacterState(name, image))
}

internal fun CharacterEpisodeState.episodesSuccess(
    seasons: List<Season>
): CharacterEpisodeState {
    return copy(
        loading = false,
        seasons = seasons
    )
}

internal fun CharacterEpisodeState.failure(): CharacterEpisodeState {
    return copy(
        loading = false,
        error = true
    )
}