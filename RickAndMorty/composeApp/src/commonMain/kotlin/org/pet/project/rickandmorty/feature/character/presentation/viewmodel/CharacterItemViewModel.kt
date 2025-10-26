package org.pet.project.rickandmorty.feature.character.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.pet.project.rickandmorty.common.presentation.BaseViewModel
import org.pet.project.rickandmorty.core.result.onFailure
import org.pet.project.rickandmorty.core.result.onSuccess
import org.pet.project.rickandmorty.feature.character.domain.entity.Character
import org.pet.project.rickandmorty.feature.character.domain.repository.CharacterRepository
import org.pet.project.rickandmorty.feature.character.presentation.intent.CharacterItemIntent
import org.pet.project.rickandmorty.feature.character.presentation.state.CharacterItemState

internal class CharacterItemViewModel(
    val id: Int,
    val characterRepository: CharacterRepository
) : BaseViewModel<CharacterItemState, CharacterItemIntent, Nothing>() {

    init {
        loadCharacter()
    }

    override fun initState(): CharacterItemState = CharacterItemState(loading = true)

    override fun onIntent(intent: CharacterItemIntent) {
        when(intent) {
            CharacterItemIntent.Refresh -> loadCharacter()
        }
    }

    private fun loadCharacter() {
        updateState { copy(loading = true, error = false) }

        viewModelScope.launch {
            characterRepository.getCharacter(id)
                .onSuccess {  character -> handleSuccess(character) }
                .onFailure { handleError() }
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