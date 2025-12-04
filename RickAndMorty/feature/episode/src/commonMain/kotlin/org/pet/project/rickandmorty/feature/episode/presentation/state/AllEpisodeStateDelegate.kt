package org.pet.project.rickandmorty.feature.episode.presentation.state

import org.pet.project.rickandmorty.feature.episode.domain.entity.Episode

internal fun AllEpisodesState.loading(): AllEpisodesState {
    return copy(loading = true, error = false)
}

internal fun AllEpisodesState.failureLoad(): AllEpisodesState {
    return copy(loading = false, error = true)
}

internal fun AllEpisodesState.uploading(): AllEpisodesState {
    return copy(uploading = true)
}

internal fun AllEpisodesState.failureUpload(): AllEpisodesState {
    return copy(uploading = false)
}

internal fun AllEpisodesState.success(
    episodes: Map<Int, List<Episode>>
): AllEpisodesState {
    return copy(loading = false, uploading = false, episodes = episodes)
}

internal fun AllEpisodesState.end(): AllEpisodesState {
    return copy(
        uploading = false,
        end = true
    )
}