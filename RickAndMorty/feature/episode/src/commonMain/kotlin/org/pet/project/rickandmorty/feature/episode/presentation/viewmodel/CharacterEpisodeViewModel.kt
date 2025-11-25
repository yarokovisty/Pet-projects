package org.pet.project.rickandmorty.feature.episode.presentation.viewmodel

import org.pet.project.rickandmorty.common.presentation.BaseViewModel
import org.pet.project.rickandmorty.feature.episode.domain.repository.EpisodeRepository
import org.pet.project.rickandmorty.feature.episode.presentation.intent.CharacterEpisodeIntent
import org.pet.project.rickandmorty.feature.episode.presentation.state.CharacterEpisodeState
import org.pet.project.rickandmorty.feature.episode.presentation.state.CharacterState

internal class CharacterEpisodeViewModel(
    private val characterId: Int,
    private val episodeRepository: EpisodeRepository
) : BaseViewModel<CharacterEpisodeState, CharacterEpisodeIntent, Nothing>() {

    override fun initState(): CharacterEpisodeState {
        return CharacterEpisodeState(
            loading = true,
            character = CharacterState("", "")
        )
    }

    override fun onIntent(intent: CharacterEpisodeIntent) {
        TODO("Not yet implemented")
    }
}