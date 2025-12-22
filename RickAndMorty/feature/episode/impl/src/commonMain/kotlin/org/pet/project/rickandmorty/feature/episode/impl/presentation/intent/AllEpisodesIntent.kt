package org.pet.project.rickandmorty.feature.episode.impl.presentation.intent

import org.pet.project.rickandmorty.common.presentation.Intent

internal interface AllEpisodesIntent : Intent {
    object Refresh : AllEpisodesIntent
    object Upload : AllEpisodesIntent
}