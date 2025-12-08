package org.pet.project.rickandmorty.feature.character.impl.presentation.viewmodel

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import org.pet.project.rickandmorty.common.presentation.BaseViewModel
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Character
import org.pet.project.rickandmorty.feature.character.api.domain.repository.CharacterRepository
import org.pet.project.rickandmorty.feature.character.impl.presentation.event.CharacterSearchEvent
import org.pet.project.rickandmorty.feature.character.impl.presentation.intent.CharacterSearchIntent
import org.pet.project.rickandmorty.feature.character.impl.presentation.state.CharacterSearchState
import org.pet.project.rickandmorty.feature.character.impl.presentation.state.failure
import org.pet.project.rickandmorty.feature.character.impl.presentation.state.initial
import org.pet.project.rickandmorty.feature.character.impl.presentation.state.inputQuery
import org.pet.project.rickandmorty.feature.character.impl.presentation.state.loading
import org.pet.project.rickandmorty.feature.character.impl.presentation.state.notFound
import org.pet.project.rickandmorty.feature.character.impl.presentation.state.success
import org.pet.project.rickandmorty.library.result.HttpException
import org.pet.project.rickandmorty.library.result.onFailure
import org.pet.project.rickandmorty.library.result.onSuccess

internal class CharacterSearchViewModel(
    private val characterRepository: CharacterRepository
) : BaseViewModel<CharacterSearchState, CharacterSearchIntent, CharacterSearchEvent>() {

    private val _search = MutableStateFlow("")

    init {
        observeSearch()
    }

    override fun initState(): CharacterSearchState = initial()

    override fun onIntent(intent: CharacterSearchIntent) {
        when(intent) {
            is CharacterSearchIntent.Clear -> clearSearch()
            is CharacterSearchIntent.OpenCharacter -> navigateToCharacterScreen(intent.characterId)
            is CharacterSearchIntent.Search -> onQueryChanged(intent.name)
            is CharacterSearchIntent.Refresh -> refreshQuery()
        }
    }

    private fun clearSearch() {
        _search.value = ""

        updateState { initial() }
    }

    private fun navigateToCharacterScreen(characterId: Int) {
        launchInScope {
            setEvent(CharacterSearchEvent.OpenCharacterScreen(characterId))
        }
    }


    private fun onQueryChanged(query: String) {
        _search.value = query
        updateState { inputQuery(query) }
    }

    private fun refreshQuery() {
        val query = _search.value

        launchInScope {
            searchCharacters(query)
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeSearch() {
        launchInScope {
            _search
                .debounce(300)
                .collectLatest { query ->
                    if (query.isBlank()) {
                        updateState { initial() }
                        return@collectLatest
                    }

                    searchCharacters(query)
                }
        }
    }

    private suspend fun searchCharacters(name: String) {
        updateState { loading() }

        characterRepository.searchCharactersByName(name)
            .onSuccess(::processSuccess)
            .onFailure(::processFailure)
    }

    private fun processSuccess(characters: List<Character>) {
        val query = stateValue.searchInputState.query

        updateState { success(name = query, characters = characters) }
    }

    private fun processFailure(t: Throwable) {
        if (t is HttpException && t.statusCode == 404) {
            val query = stateValue.searchInputState.query
            updateState { notFound(name = query) }
        } else {
            updateState { failure() }
        }
    }
}