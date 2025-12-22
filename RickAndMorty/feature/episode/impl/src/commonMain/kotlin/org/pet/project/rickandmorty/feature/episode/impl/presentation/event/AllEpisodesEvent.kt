package org.pet.project.rickandmorty.feature.episode.impl.presentation.event

import org.pet.project.rickandmorty.common.presentation.Event

internal sealed class AllEpisodesEvent(open val message: String?) : Event {
    class OnReachEnd(override val message: String) : AllEpisodesEvent(message)
    class ErrorUploadEpisodes(override val message: String) : AllEpisodesEvent(message)
}