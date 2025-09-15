package org.pet.project.rickandmorty.feature.character.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.pet.project.rickandmorty.common.presentation.BaseViewModel
import org.pet.project.rickandmorty.common.utils.EmptyResult
import org.pet.project.rickandmorty.common.utils.asSuccess
import org.pet.project.rickandmorty.common.utils.isFailure
import org.pet.project.rickandmorty.feature.character.domain.entity.Character
import org.pet.project.rickandmorty.feature.character.domain.repository.CharacterRepository
import org.pet.project.rickandmorty.feature.character.presentation.event.CharacterListEvent
import org.pet.project.rickandmorty.feature.character.presentation.intent.CharacterListIntent
import org.pet.project.rickandmorty.feature.character.presentation.state.CharacterListState

internal class CharacterListViewModel(
    private val characterRepository: CharacterRepository
) : BaseViewModel<CharacterListState, CharacterListIntent, CharacterListEvent>() {

    init {
        viewModelScope.launch(Dispatchers.Default) {
            characterRepository.characters.collect { result ->
                when {
                    result.isFailure() -> processFailure()
                    result is EmptyResult -> processEndReached()
                    else -> processSuccess(result.asSuccess().value)
                }
            }
        }

        loadNextCharacters()
    }

    override fun initState(): CharacterListState {
        return CharacterListState(skeleton = true)
    }

    override fun onIntent(intent: CharacterListIntent) {
        when (intent) {
            CharacterListIntent.UploadCharacterList -> loadNextCharacters()
            is CharacterListIntent.OpenCharacterScreen -> {}
        }
    }

    private fun loadNextCharacters() {
        if (!currentState.uploadAllCharacters) {
            if (!currentState.skeleton) {
                updateState { copy(isLoadingMore = true) }
            }

            viewModelScope.launch {
                characterRepository.loadCharacterList()
            }
        }
    }

    private fun processFailure() {
        when {
            currentState.skeleton -> {
                updateState { copy(skeleton = false, error = true) }
            }

            currentState.isLoadingMore -> {
                updateState { copy(isLoadingMore = false) }
                setEvent(CharacterListEvent.ErrorUploadCharacters)
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