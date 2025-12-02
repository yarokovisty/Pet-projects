package org.pet.project.rickandmorty.feature.episode.presentation.viewmodel

import org.pet.project.rickandmorty.common.presentation.BaseViewModel
import org.pet.project.rickandmorty.feature.episode.domain.entity.Episode
import org.pet.project.rickandmorty.feature.episode.domain.entity.EpisodeState
import org.pet.project.rickandmorty.feature.episode.domain.repository.EpisodeRepository
import org.pet.project.rickandmorty.feature.episode.domain.usecase.GetEpisodesUseCase
import org.pet.project.rickandmorty.feature.episode.presentation.event.AllEpisodesEvent
import org.pet.project.rickandmorty.feature.episode.presentation.intent.AllEpisodesIntent
import org.pet.project.rickandmorty.feature.episode.presentation.state.AllEpisodesState
import org.pet.project.rickandmorty.feature.episode.presentation.state.failureLoad
import org.pet.project.rickandmorty.feature.episode.presentation.state.failureUpload
import org.pet.project.rickandmorty.feature.episode.presentation.state.loading
import org.pet.project.rickandmorty.feature.episode.presentation.state.success
import org.pet.project.rickandmorty.feature.episode.presentation.state.uploading

internal class AllEpisodesViewModel(
    private val episodeRepository: EpisodeRepository,
    private val getEpisodesUseCase: GetEpisodesUseCase
) : BaseViewModel<AllEpisodesState, AllEpisodesIntent, AllEpisodesEvent>() {

    init {
        observeEpisode()
        loadEpisodes()
    }

    override fun initState(): AllEpisodesState {
        return AllEpisodesState()
    }

    override fun onIntent(intent: AllEpisodesIntent) {
        when(intent) {
            is AllEpisodesIntent.Refresh -> loadEpisodes()
            is AllEpisodesIntent.Upload -> uploadEpisodes()
        }
    }

    private fun observeEpisode() {
        launchInScope {
            episodeRepository.episodes.collect {  result ->
                when(result) {
                    is EpisodeState.Error -> processError()
                    is EpisodeState.Success -> processSuccess(result.value)
                    else -> {}
                }
            }
        }
    }

    private fun loadEpisodes() {
        updateState { loading() }

        launchInScope {
            episodeRepository.loadEpisodes()
        }
    }

    private fun uploadEpisodes() {
        updateState { uploading() }

        launchInScope {
            episodeRepository.loadEpisodes()
        }
    }

    private fun processSuccess(newEpisodes: Map<Int, List<Episode>>) {
        launchInScope {
            val allEpisodes = getEpisodesUseCase(
                currentEpisodes = stateValue.episodes,
                newEpisodes = newEpisodes
            )

            updateState { success(allEpisodes) }
        }
    }

    private fun processError() {
        updateState {
            if (stateValue.loading) failureLoad() else failureUpload()
        }
    }
}