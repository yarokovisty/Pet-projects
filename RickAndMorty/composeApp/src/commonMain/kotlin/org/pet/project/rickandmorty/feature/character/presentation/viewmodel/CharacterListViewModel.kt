package org.pet.project.rickandmorty.feature.character.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
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
        viewModelScope.launch(Dispatchers.Default) {
            when (intent) {
                CharacterListIntent.Refresh -> refreshCharacters()
                CharacterListIntent.Upload -> loadNextCharacters()
                is CharacterListIntent.OpenCharacterScreen -> {}
            }
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
        viewModelScope.launch(Dispatchers.IO) {
            characterRepository.loadCharacterList()
        }
    }
    private suspend fun refreshCharacters() {
        updateState { copy(skeleton = true, error = false) }
        characterRepository.loadCharacterList()
    }

    private suspend fun loadNextCharacters() {
        if (currentState.uploadAllCharacters) return

        updateState { copy(isLoadingMore = true) }
        characterRepository.loadCharacterList()
    }

    private fun processFailure() {
        when {
            currentState.skeleton -> {
                updateState { copy(skeleton = false, error = true) }
            }

            currentState.isLoadingMore -> {
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
        val oldCharacters = currentState.characters
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