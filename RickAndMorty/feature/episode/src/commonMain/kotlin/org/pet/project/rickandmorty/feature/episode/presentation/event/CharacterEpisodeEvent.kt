package org.pet.project.rickandmorty.feature.episode.presentation.event

import org.pet.project.rickandmorty.common.presentation.Event

sealed interface CharacterEpisodeEvent : Event {
    object Back : CharacterEpisodeEvent
}