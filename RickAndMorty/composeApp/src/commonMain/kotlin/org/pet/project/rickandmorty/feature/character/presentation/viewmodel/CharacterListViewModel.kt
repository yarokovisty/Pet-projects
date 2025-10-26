package org.pet.project.rickandmorty.feature.character.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.pet.project.rickandmorty.common.presentation.BaseViewModel
import org.pet.project.rickandmorty.core.result.EmptyResult
import org.pet.project.rickandmorty.core.result.asSuccess
import org.pet.project.rickandmorty.core.result.isFailure
import org.pet.project.rickandmorty.feature.character.domain.entity.Character
import org.pet.project.rickandmorty.feature.character.domain.repository.CharacterRepository
import org.pet.project.rickandmorty.feature.character.presentation.event.CharacterListEvent
import org.pet.project.rickandmorty.feature.character.presentation.intent.CharacterListIntent
import org.pet.project.rickandmorty.feature.character.presentation.state.CharacterListState

internal class CharacterListViewModel(
    private val characterRepository: CharacterRepository
) : BaseViewModel<CharacterListState, CharacterListIntent, CharacterListEvent>() {

    init {
        observeCharacters()
        loadCharacters()
    }

    override fun initState(): CharacterListState {
        return CharacterListState(skeleton = true)
    }

    override fun onIntent(intent: CharacterListIntent) {
        when (intent) {
            CharacterListIntent.Refresh -> refreshCharacters()
            CharacterListIntent.Upload -> loadNextCharacters()
        }
    }

    private fun observeCharacters() {
        viewModelScope.launch {
            characterRepository.characters.collect { result ->
                when {
                    result.isFailure() -> processFailure()
                    result is EmptyResult -> processEndReached()
                    else -> processSuccess(result.asSuccess().value)
                }
            }
        }
    }

    private fun loadCharacters() {
        viewModelScope.launch {
            characterRepository.loadCharacterList()
        }
    }
    private fun refreshCharacters() {
        updateState { copy(skeleton = true, error = false) }
        viewModelScope.launch {
            characterRepository.loadCharacterList()
        }
    }

    private fun loadNextCharacters() {
        if (currentStateValue.uploadAllCharacters) return

        updateState { copy(isLoadingMore = true) }
        viewModelScope.launch {
            characterRepository.loadCharacterList()
        }
    }

    private fun processFailure() {
        when {
            currentStateValue.skeleton -> {
                updateState { copy(skeleton = false, error = true) }
            }

            currentStateValue.isLoadingMore -> {
                updateState { copy(isLoadingMore = false) }
                viewModelScope.launch {
                    setEvent(CharacterListEvent.ErrorUploadCharacters)
                }
            }
        }
    }

    private fun processEndReached() {
        updateState { copy(uploadAllCharacters = true) }
    }

    private fun processSuccess(characters: List<Character>) {
        val oldCharacters = currentStateValue.characters
        val newCharacters = oldCharacters + characters

        updateState {
            copy(
                skeleton = false,
                isLoadingMore = false,
                characters = newCharacters
            )
        }

    }

}