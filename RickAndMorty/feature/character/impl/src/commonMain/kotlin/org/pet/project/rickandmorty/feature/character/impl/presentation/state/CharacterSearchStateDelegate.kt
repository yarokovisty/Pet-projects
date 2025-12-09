package org.pet.project.rickandmorty.feature.character.impl.presentation.state

import org.pet.project.rickandmorty.feature.character.api.domain.entity.Character

internal fun initial(): CharacterSearchState {
    return CharacterSearchState()
}

internal fun CharacterSearchState.inputQuery(query: String): CharacterSearchState {
    return copy(searchInputState = searchInputState.copy(query = query))
}

internal fun CharacterSearchState.loading(): CharacterSearchState {
    return copy(
        searchResultState = searchResultState.copy(loading = true, error = false)
    )
}

internal fun CharacterSearchState.success(
    name: String,
    characters: List<Character>,
    filters: List<FilterState>
): CharacterSearchState {
    return copy(
        searchResultState = searchResultState.copy(
            loading = false,
            content = SearchContentState(
                name = name,
                numFound = characters.size,
                characters = characters
            ),
            filter = FiltersResultState(
                filters = filters,
                filteredCharacters = characters
            )
        )
    )
}

internal fun CharacterSearchState.notFound(name: String): CharacterSearchState {
    return copy(
        searchResultState = searchResultState.copy(
            loading = false,
            query = name,
            notFound = true
        )
    )
}

internal fun CharacterSearchState.failure(): CharacterSearchState {
    return copy(
        searchResultState = searchResultState.copy(loading = false, error = true)
    )
}