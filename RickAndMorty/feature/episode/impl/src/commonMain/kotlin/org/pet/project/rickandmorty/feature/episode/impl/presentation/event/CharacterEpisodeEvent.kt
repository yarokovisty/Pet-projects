package org.pet.project.rickandmorty.feature.episode.impl.presentation.event

import org.pet.project.rickandmorty.common.presentation.Event

sealed interface CharacterEpisodeEvent : Event {
    object Back : CharacterEpisodeEvent
}