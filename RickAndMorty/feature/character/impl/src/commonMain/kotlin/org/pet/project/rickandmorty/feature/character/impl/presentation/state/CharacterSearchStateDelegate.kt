package org.pet.project.rickandmorty.feature.character.impl.presentation.state

import org.pet.project.rickandmorty.feature.character.api.domain.entity.Character

internal fun initial(): CharacterSearchState {
    return CharacterSearchState()
}

internal fun CharacterSearchState.inputQuery(query: String): CharacterSearchState {
    return copy(searchInputState = searchInputState.copy(query = query))
}

internal fun CharacterSearchState.loading(): CharacterSearchState {
    return copy(searchResultState = SearchResultState.Loading)
}

internal fun CharacterSearchState.success(
    name: String,
    characters: List<Character>
): CharacterSearchState {
    return copy(
        searchResultState = SearchResultState.Content(
            numFound = characters.size,
            name = name,
            characters = characters
        )
    )
}

internal fun CharacterSearchState.notFound(name: String): CharacterSearchState {
    return copy(searchResultState = SearchResultState.NotFound(name))
}

internal fun CharacterSearchState.failure(): CharacterSearchState {
    return copy(searchResultState = SearchResultState.Error)
}