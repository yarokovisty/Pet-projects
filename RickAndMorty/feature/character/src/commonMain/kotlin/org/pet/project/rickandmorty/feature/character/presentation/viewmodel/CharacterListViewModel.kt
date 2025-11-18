package org.pet.project.rickandmorty.feature.character.presentation.viewmodel

import org.pet.project.rickandmorty.common.presentation.BaseViewModel
import org.pet.project.rickandmorty.feature.character.domain.entity.Character
import org.pet.project.rickandmorty.feature.character.domain.repository.CharacterRepository
import org.pet.project.rickandmorty.feature.character.presentation.event.CharacterListEvent
import org.pet.project.rickandmorty.feature.character.presentation.intent.CharacterListIntent
import org.pet.project.rickandmorty.feature.character.presentation.state.CharacterListState
import org.pet.project.rickandmorty.library.result.EmptyResult
import org.pet.project.rickandmorty.library.result.asSuccess
import org.pet.project.rickandmorty.library.result.isFailure
import rickandmorty.feature.character.generated.resources.Res
import rickandmorty.feature.character.generated.resources.character_error_upload


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
                when {
                    result.isFailure() -> processFailure()
                    result is EmptyResult -> processEndReached()
                    else -> processSuccess(result.asSuccess().value)
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
        updateState { copy(skeleton = true, error = false) }
        launchInScope {
            characterRepository.loadCharacterList()
        }
    }

    private fun loadNextCharacters() {
        if (stateValue.uploadAllCharacters) return

        updateState { copy(isLoadingMore = true) }
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
                updateState { copy(skeleton = false, error = true) }
            }

            stateValue.isLoadingMore -> {
                updateState { copy(isLoadingMore = false) }
                launchInScope {
                    setEvent(
                        CharacterListEvent.Error(Res.string.character_error_upload)
                    )
                }
            }
        }
    }

    private fun processEndReached() {
        updateState { copy(uploadAllCharacters = true) }
    }

    private fun processSuccess(characters: List<Character>) {
        val oldCharacters = stateValue.characters
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