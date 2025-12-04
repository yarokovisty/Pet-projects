package org.pet.project.rickandmorty.feature.episode.presentation.viewmodel

import org.jetbrains.compose.resources.getString
import org.pet.project.rickandmorty.common.presentation.BaseViewModel
import org.pet.project.rickandmorty.feature.episode.domain.entity.Episode
import org.pet.project.rickandmorty.feature.episode.domain.entity.EpisodeState
import org.pet.project.rickandmorty.feature.episode.domain.repository.EpisodeRepository
import org.pet.project.rickandmorty.feature.episode.domain.usecase.GetEpisodesUseCase
import org.pet.project.rickandmorty.feature.episode.presentation.event.AllEpisodesEvent
import org.pet.project.rickandmorty.feature.episode.presentation.intent.AllEpisodesIntent
import org.pet.project.rickandmorty.feature.episode.presentation.state.AllEpisodesState
import org.pet.project.rickandmorty.feature.episode.presentation.state.end
import org.pet.project.rickandmorty.feature.episode.presentation.state.failureLoad
import org.pet.project.rickandmorty.feature.episode.presentation.state.failureUpload
import org.pet.project.rickandmorty.feature.episode.presentation.state.loading
import org.pet.project.rickandmorty.feature.episode.presentation.state.success
import org.pet.project.rickandmorty.feature.episode.presentation.state.uploading
import rickandmorty.feature.episode.generated.resources.Res
import rickandmorty.feature.episode.generated.resources.all_episode_uploaded
import rickandmorty.feature.episode.generated.resources.error_upload_episode

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
                    is EpisodeState.End -> processEnd()
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
        if (stateValue.end) return

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
        if (stateValue.loading) {
            updateState { failureLoad() }
        } else {
            updateState { failureUpload() }

            launchInScope {
                val message = getString(Res.string.error_upload_episode)
                setEvent(AllEpisodesEvent.ErrorUploadEpisodes(message))
            }
        }
    }

    private fun processEnd() {
        updateState { end() }

        launchInScope {
            val message = getString(Res.string.all_episode_uploaded)
            setEvent(AllEpisodesEvent.OnReachEnd(message))
        }
    }
}