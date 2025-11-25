package org.pet.project.rickandmorty.feature.character.impl.presentation.viewmodel

import org.pet.project.rickandmorty.common.presentation.BaseViewModel
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Character
import org.pet.project.rickandmorty.feature.character.api.domain.entity.CharacterState
import org.pet.project.rickandmorty.feature.character.api.domain.repository.CharacterRepository
import org.pet.project.rickandmorty.feature.character.impl.presentation.event.CharacterListEvent
import org.pet.project.rickandmorty.feature.character.impl.presentation.intent.CharacterListIntent
import org.pet.project.rickandmorty.feature.character.impl.presentation.state.CharacterListState
import org.pet.project.rickandmorty.feature.character.impl.presentation.state.endReached
import org.pet.project.rickandmorty.feature.character.impl.presentation.state.failure
import org.pet.project.rickandmorty.feature.character.impl.presentation.state.refresh
import org.pet.project.rickandmorty.feature.character.impl.presentation.state.success
import org.pet.project.rickandmorty.feature.character.impl.presentation.state.uploading
import rickandmorty.feature.character.impl.generated.resources.Res
import rickandmorty.feature.character.impl.generated.resources.character_error_upload


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
            is CharacterListIntent.OpenCharacterScreen -> notifyForNavigateToCharacterScreen(intent.id)
        }
    }

    private fun observeCharacters() {
        launchInScope {
            characterRepository.characters.collect { result ->
                when(result) {
                    is CharacterState.Error -> processFailure()
                    is CharacterState.End -> processEndReached()
                    is CharacterState.Success -> processSuccess(result.value)
                    else -> {}
                }
            }
        }
    }

    private fun loadCharacters() {
        launchInScope {
            characterRepository.loadCharacterList()
        }
    }
    private fun refreshCharacters() {
        updateState { refresh() }
        launchInScope {
            characterRepository.loadCharacterList()
        }
    }

    private fun loadNextCharacters() {
        if (stateValue.uploadAllCharacters) return

        updateState { uploading() }
        launchInScope {
            characterRepository.loadCharacterList()
        }
    }

    private fun notifyForNavigateToCharacterScreen(characterId: Int) {
        launchInScope {
            setEvent(CharacterListEvent.OpenCharacterScreen(characterId))
        }
    }

    private fun processFailure() {
        when {
            stateValue.skeleton -> {
                updateState { failure(true) }
            }

            stateValue.isLoadingMore -> {
                updateState { failure(false) }
                launchInScope {
                    setEvent(
                        CharacterListEvent.Error(Res.string.character_error_upload)
                    )
                }
            }
        }
    }

    private fun processEndReached() {
        updateState { endReached() }
    }

    private fun processSuccess(characters: List<Character>) {
        val oldCharacters = stateValue.characters
        val newCharacters = oldCharacters + characters

        updateState { success(newCharacters) }
    }
}