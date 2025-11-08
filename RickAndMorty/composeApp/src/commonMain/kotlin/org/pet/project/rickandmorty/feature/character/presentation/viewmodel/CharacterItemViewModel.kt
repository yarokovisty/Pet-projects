package org.pet.project.rickandmorty.feature.character.presentation.viewmodel

import org.pet.project.rickandmorty.common.presentation.BaseViewModel
import org.pet.project.rickandmorty.core.result.onFailure
import org.pet.project.rickandmorty.core.result.onSuccess
import org.pet.project.rickandmorty.feature.character.domain.entity.Character
import org.pet.project.rickandmorty.feature.character.domain.repository.CharacterRepository
import org.pet.project.rickandmorty.feature.character.presentation.event.CharacterItemEvent
import org.pet.project.rickandmorty.feature.character.presentation.intent.CharacterItemIntent
import org.pet.project.rickandmorty.feature.character.presentation.state.CharacterItemState

internal class CharacterItemViewModel(
    val id: Int,
    val characterRepository: CharacterRepository
) : BaseViewModel<CharacterItemState, CharacterItemIntent, CharacterItemEvent>() {

    init {
        loadCharacter()
    }

    override fun initState(): CharacterItemState = CharacterItemState()

    override fun onIntent(intent: CharacterItemIntent) {
        when(intent) {
            is CharacterItemIntent.Refresh -> loadCharacter()
            is CharacterItemIntent.Back -> navigateBack()
            is CharacterItemIntent.OpenOriginScreen -> navigateToLocationScreen(intent.name)
            is CharacterItemIntent.OpenLocationScreen -> navigateToLocationScreen(intent.name)
            is CharacterItemIntent.OpenAllEpisodes -> {}
        }
    }

    private fun loadCharacter() {
        updateState { copy(loading = true, error = false) }

        launchInScope {
            characterRepository.getCharacter(id)
                .onSuccess {  character -> handleSuccess(character) }
                .onFailure { handleError() }
        }
    }

    private fun navigateBack() {
        launchInScope {
            setEvent(CharacterItemEvent.Back)
        }
    }
    private fun navigateToLocationScreen(locationName: String) {
        launchInScope {
            setEvent(CharacterItemEvent.OpenLocationScreen(locationName))
        }
    }

    private fun handleSuccess(character: Character) {
        updateState {
            copy(
                loading = false,
                error = false,
                character = character
            )
        }
    }

    private fun handleError() {
        updateState {
            copy(
                loading = false,
                error = true
            )
        }
    }
}