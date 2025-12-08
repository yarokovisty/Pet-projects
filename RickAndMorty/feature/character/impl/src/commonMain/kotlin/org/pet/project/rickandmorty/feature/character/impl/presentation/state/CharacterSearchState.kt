package org.pet.project.rickandmorty.feature.character.impl.presentation.state

import org.pet.project.rickandmorty.common.presentation.State
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Character

internal data class CharacterSearchState(
    val searchInputState: SearchInputState = SearchInputState(),
    val searchResultState: SearchResultState = SearchResultState.Initial
) : State

internal data class SearchInputState(
    val query: String = ""
) {
    val clearShow: Boolean get() = query.isNotEmpty()
}



internal sealed interface SearchResultState {
    object Initial : SearchResultState
    object Loading : SearchResultState
    object Error : SearchResultState
    class NotFound(val name: String) : SearchResultState

    class Content(
        val numFound: Int,
        val name: String,
        val characters: List<Character>
    ) : SearchResultState
}