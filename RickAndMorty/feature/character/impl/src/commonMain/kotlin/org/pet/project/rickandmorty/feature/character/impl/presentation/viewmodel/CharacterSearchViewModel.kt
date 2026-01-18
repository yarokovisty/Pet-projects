package org.pet.project.rickandmorty.feature.character.impl.presentation.viewmodel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.getString
import org.pet.project.rickandmorty.common.presentation.BaseViewModel
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Character
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Filter
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Gender
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Status
import org.pet.project.rickandmorty.feature.character.api.domain.repository.CharacterRepository
import org.pet.project.rickandmorty.feature.character.impl.domain.usecase.FilterCharacterUseCase
import org.pet.project.rickandmorty.feature.character.impl.domain.usecase.GetCountCharacterByFilterUseCase
import org.pet.project.rickandmorty.feature.character.impl.presentation.event.CharacterSearchEvent
import org.pet.project.rickandmorty.feature.character.impl.presentation.intent.CharacterSearchIntent
import org.pet.project.rickandmorty.feature.character.impl.presentation.mapper.toFilter
import org.pet.project.rickandmorty.feature.character.impl.presentation.state.CharacterSearchState
import org.pet.project.rickandmorty.feature.character.impl.presentation.state.clickFilterToggle
import org.pet.project.rickandmorty.feature.character.impl.presentation.state.disableFilterDropdownMenu
import org.pet.project.rickandmorty.feature.character.impl.presentation.state.expandFilterDropdownMenu
import org.pet.project.rickandmorty.feature.character.impl.presentation.state.failure
import org.pet.project.rickandmorty.feature.character.impl.presentation.state.inputQuery
import org.pet.project.rickandmorty.feature.character.impl.presentation.state.loading
import org.pet.project.rickandmorty.feature.character.impl.presentation.state.notFound
import org.pet.project.rickandmorty.feature.character.impl.presentation.state.success
import org.pet.project.rickandmorty.library.result.HttpException
import org.pet.project.rickandmorty.library.result.onFailure
import org.pet.project.rickandmorty.library.result.onSuccess
import rickandmorty.feature.character.impl.generated.resources.Res
import rickandmorty.feature.character.impl.generated.resources.character_gender_title
import rickandmorty.feature.character.impl.generated.resources.character_status_title

internal class CharacterSearchViewModel(
    private val characterRepository: CharacterRepository,
    private val getCountCharacterByFilterUseCase: GetCountCharacterByFilterUseCase,
    private val filterCharacterUseCase: FilterCharacterUseCase
) : BaseViewModel<CharacterSearchState, CharacterSearchIntent, CharacterSearchEvent>() {

    private val _search = MutableStateFlow("")

    init {
        observeSearch()
    }

    override fun initState(): CharacterSearchState = CharacterSearchState.INITIAL

    override fun onIntent(intent: CharacterSearchIntent) {
        when(intent) {
            is CharacterSearchIntent.Clear -> clearSearch()
            is CharacterSearchIntent.ClickFilter -> clickFilter(intent.filterToggle)
            is CharacterSearchIntent.OpenCharacter -> navigateToCharacterScreen(intent.characterId)
            is CharacterSearchIntent.Refresh -> refreshQuery()
            is CharacterSearchIntent.Search -> onQueryChanged(intent.name)
            is CharacterSearchIntent.ToggleFilterMenu -> toggleFilterMenu()
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeSearch() {
        launchInScope {
            _search
                .debounce(300)
                .collectLatest { query ->
                    if (query.isBlank()) {
                        updateState { CharacterSearchState.INITIAL }
                        return@collectLatest
                    }

                    searchCharacters(query)
                }
        }
    }

    private fun clearSearch() {
        _search.value = ""

        updateState { CharacterSearchState.INITIAL }
    }

    private fun clickFilter(filterToggle: Filter) {
        launchInScope {
            val filters = stateValue.filterMenuState.filters.mapValues { (_, filterList) ->
                filterList.map { filter ->
                    if (filter.filter == filterToggle.filter) {
                        filter.copy(selected = !filter.selected)
                    } else {
                        filter
                    }
                }
            }
            val filteredCharacters = stateValue.searchResultState.content?.characters?.let { characters ->
                filterCharacterUseCase(characters, filters)
            } ?: emptyList()

            updateState { clickFilterToggle(filteredCharacters, filters) }
        }
    }

    private suspend fun filterCharacters(filters: Map<String, List<Filter>>): List<Character> = withContext(Dispatchers.Default) {
        stateValue.searchResultState.content?.characters?.filter { character ->
            val selectedFilters = filters.values.flatten().filter { it.selected }

            if (selectedFilters.isEmpty()) return@filter true

            val selectedGenders = selectedFilters.filter { it.filter is Gender }
            val selectedStatuses = selectedFilters.filter { it.filter is Status }

            val matchesGender = selectedGenders.isEmpty() || selectedGenders.any { it.filter == character.gender }

            val matchesStatus = selectedStatuses.isEmpty() || selectedStatuses.any { it.filter == character.status }

            matchesGender && matchesStatus
        } ?: emptyList()
    }

    private fun navigateToCharacterScreen(characterId: Int) {
        launchInScope {
            setEvent(CharacterSearchEvent.OpenCharacterScreen(characterId))
        }
    }

    private fun refreshQuery() {
        val query = _search.value

        launchInScope {
            searchCharacters(query)
        }
    }

    // поменять название функции, так как on используется для колбеков
    private fun onQueryChanged(query: String) {
        _search.value = query
        updateState { inputQuery(query) }
    }

    private fun toggleFilterMenu() {
        if (stateValue.filterMenuState.expanded) updateState { disableFilterDropdownMenu() }
        else updateState { expandFilterDropdownMenu() }
    }

    private suspend fun searchCharacters(name: String) {
        updateState { loading() }

        characterRepository.searchAllCharactersByName(name)
            .onSuccess { processSuccess(it) }
            .onFailure(::processFailure)
    }

    private suspend fun processSuccess(characters: List<Character>) {
        val query = stateValue.searchInputState.query
        val filters = mapToFilters(characters)

        updateState { success(name = query, characters = characters, filters = filters) }
    }

    private fun processFailure(t: Throwable) {
        if (t is HttpException && t.statusCode == 404) {
            val query = stateValue.searchInputState.query
            updateState { notFound(name = query) }
        } else {
            updateState { failure() }
        }
    }

    private suspend fun mapToFilters(characters: List<Character>): Map<String, List<Filter>> {
        val genderKey = getString(Res.string.character_gender_title)
        val statusKey = getString(Res.string.character_status_title)

        return getCountCharacterByFilterUseCase(characters)
            .entries
            .groupBy(
                keySelector = { (filter, _) ->
                    when (filter) {
                        is Gender -> genderKey
                        is Status -> statusKey
                        else -> error("Unknown filter type: ${filter::class.simpleName}")
                    }
                },
                valueTransform = { (filter, count) ->
                    when (filter) {
                        is Gender -> filter.toFilter(count)
                        is Status -> filter.toFilter(count)
                        else -> error("Unknown filter type: ${filter::class.simpleName}")
                    }
                }
            )
    }
}