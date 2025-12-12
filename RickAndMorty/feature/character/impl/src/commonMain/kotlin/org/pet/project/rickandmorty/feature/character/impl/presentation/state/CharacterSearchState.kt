package org.pet.project.rickandmorty.feature.character.impl.presentation.state

import org.pet.project.rickandmorty.common.presentation.State
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Character
import org.pet.project.rickandmorty.feature.character.api.domain.entity.CharacterFilter

internal data class CharacterSearchState(
    val searchInputState: SearchInputState = SearchInputState(),
    val searchResultState: SearchResultState = SearchResultState(),
    val filterMenuState: FilterMenuState = FilterMenuState()
) : State

internal data class SearchInputState(
    val query: String = ""
) {
    val clearShow: Boolean get() = query.isNotEmpty()
}

internal data class SearchResultState(
    val loading: Boolean = false,
    val error: Boolean = false,
    val notFound: Boolean = false,
    val query: String = "",
    val content: SearchContentState? = null
)

internal data class SearchContentState(
    val name: String,
    val numFound: Int,
    val characters: List<Character>,
    val filteredCharacters: List<Character> = emptyList()
)

internal data class FilterMenuState(
    val showMenuIcon: Boolean = false,
    val expanded: Boolean = false,
    val filters: Map<String, List<FilterState>> = emptyMap()
)

internal data class FilterState(
    val amount: Int,
    val name: String,
    val selected: Boolean,
    val filter: CharacterFilter
)