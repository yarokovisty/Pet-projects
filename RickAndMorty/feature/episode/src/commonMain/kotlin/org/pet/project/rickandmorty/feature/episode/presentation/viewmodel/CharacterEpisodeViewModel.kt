package org.pet.project.rickandmorty.feature.episode.presentation.viewmodel

import org.pet.project.rickandmorty.common.presentation.BaseViewModel
import org.pet.project.rickandmorty.feature.character.api.domain.repository.CharacterRepository
import org.pet.project.rickandmorty.feature.episode.domain.repository.EpisodeRepository
import org.pet.project.rickandmorty.feature.episode.presentation.event.CharacterEpisodeEvent
import org.pet.project.rickandmorty.feature.episode.presentation.intent.CharacterEpisodeIntent
import org.pet.project.rickandmorty.feature.episode.presentation.state.CharacterEpisodeState
import org.pet.project.rickandmorty.feature.episode.presentation.state.characterSuccess
import org.pet.project.rickandmorty.feature.episode.presentation.state.episodesSuccess
import org.pet.project.rickandmorty.feature.episode.presentation.state.failure
import org.pet.project.rickandmorty.feature.episode.presentation.state.loading
import org.pet.project.rickandmorty.library.result.onFailure
import org.pet.project.rickandmorty.library.result.onSuccess

internal class CharacterEpisodeViewModel(
    private val characterId: Int,
    private val characterRepository: CharacterRepository,
    private val episodeRepository: EpisodeRepository,
) : BaseViewModel<CharacterEpisodeState, CharacterEpisodeIntent, CharacterEpisodeEvent>() {

    init {
        loadCharacter()
    }

    override fun initState(): CharacterEpisodeState = CharacterEpisodeState()

    override fun onIntent(intent: CharacterEpisodeIntent) {
        when(intent) {
            CharacterEpisodeIntent.Refresh -> loadCharacter()
            CharacterEpisodeIntent.Back -> navigateToBack()
        }
    }

    private fun loadCharacter() {
        updateState { loading() }

        launchInScope {
            characterRepository.getCharacter(characterId)
                .onSuccess { character ->
                    updateState { characterSuccess(character.name, character.image) }
                    loadEpisodes(character.episodes)
                }
                .onFailure {
                    updateState { failure() }
                }
        }
    }

    private suspend fun loadEpisodes(episodeIds: List<Int>) {
        episodeRepository.getEpisodes(episodeIds)
            .onSuccess { seasons ->
                updateState { episodesSuccess(seasons) }
            }
            .onFailure {
                updateState { failure() }
            }
    }

    private fun navigateToBack() {
        launchInScope {
            setEvent(CharacterEpisodeEvent.Back)
        }
    }
}