package org.pet.project.rickandmorty.feature.character.impl.presentation.state

import org.pet.project.rickandmorty.feature.character.api.domain.entity.Character
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Filter

internal fun CharacterSearchState.inputQuery(query: String): CharacterSearchState {
    return copy(searchInputState = searchInputState.copy(query = query))
}

internal fun CharacterSearchState.loading(): CharacterSearchState {
    return copy(
        searchResultState = searchResultState.copy(loading = true, error = false),
        filterMenuState = FilterMenuState.INITIAL
    )
}

internal fun CharacterSearchState.success(
    name: String,
    characters: List<Character>,
    filters: Map<String, List<Filter>>
): CharacterSearchState {
    return copy(
        searchResultState = searchResultState.copy(
            loading = false,
            content = SearchContentState(
                name = name,
                characters = characters,
                filteredCharacters = characters
            )
        ),
        filterMenuState = FilterMenuState(showMenuIcon = true, expanded = false, filters = filters)
    )
}

internal fun CharacterSearchState.notFound(name: String): CharacterSearchState {
    return copy(
        searchResultState = searchResultState.copy(
            loading = false,
            query = name,
            notFound = true
        ),
        filterMenuState = FilterMenuState.INITIAL
    )
}

internal fun CharacterSearchState.failure(): CharacterSearchState {
    return copy(
        searchResultState = searchResultState.copy(loading = false, error = true),
        filterMenuState = FilterMenuState.INITIAL
    )
}
internal fun CharacterSearchState.expandFilterDropdownMenu(): CharacterSearchState {
    return copy(filterMenuState = filterMenuState.copy(expanded = true))
}

internal fun CharacterSearchState.disableFilterDropdownMenu(): CharacterSearchState {
    return copy(filterMenuState = filterMenuState.copy(expanded = false))
}

internal fun CharacterSearchState.clickFilterToggle(
    filteredCharacters: List<Character>,
    filters: Map<String, List<Filter>>
): CharacterSearchState {
    return copy(
        searchResultState = searchResultState.copy(
            content = searchResultState.content?.copy(filteredCharacters = filteredCharacters)
        ),
        filterMenuState = filterMenuState.copy(filters = filters)
    )
}