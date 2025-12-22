package org.pet.project.rickandmorty.feature.character.impl.presentation.state

import org.pet.project.rickandmorty.common.presentation.State
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Character
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Filter

internal data class CharacterSearchState(
    val searchInputState: SearchInputState,
    val searchResultState: SearchResultState,
    val filterMenuState: FilterMenuState,
) : State {

    companion object {

        val INITIAL = CharacterSearchState(
            searchInputState = SearchInputState.INITIAL,
            searchResultState = SearchResultState.INITIAL,
            filterMenuState = FilterMenuState.INITIAL
        )
    }
}

internal data class SearchInputState(
    val query: String,
) {
    val clearShow: Boolean get() = query.isNotEmpty()

    companion object {

        val INITIAL = SearchInputState(query = "")
    }
}

internal data class SearchResultState(
    val loading: Boolean,
    val error: Boolean,
    val notFound: Boolean,
    val query: String,
    val content: SearchContentState?,
) {

    companion object {
        val INITIAL = SearchResultState(
            loading = false,
            error = false,
            notFound = false,
            query = "",
            content = null
        )
    }
}

internal data class SearchContentState(
    val name: String,
    val characters: List<Character>,
    val filteredCharacters: List<Character>
) {
    val numFound: Int get() = filteredCharacters.size
}

internal data class FilterMenuState(
    val showMenuIcon: Boolean,
    val expanded: Boolean,
    val filters: Map<String, List<Filter>>
) {

    companion object {

        val INITIAL = FilterMenuState(
            showMenuIcon = false,
            expanded = false,
            filters = emptyMap()
        )
    }
}

