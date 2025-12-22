package org.pet.project.rickandmorty.feature.episode.impl.presentation.intent

import org.pet.project.rickandmorty.common.presentation.Intent

sealed interface CharacterEpisodeIntent : Intent {
    object Refresh : CharacterEpisodeIntent
    object Back : CharacterEpisodeIntent
}