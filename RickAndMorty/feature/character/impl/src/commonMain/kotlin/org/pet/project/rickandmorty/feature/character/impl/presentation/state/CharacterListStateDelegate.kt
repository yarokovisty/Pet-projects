package org.pet.project.rickandmorty.feature.character.impl.presentation.state

import org.pet.project.rickandmorty.feature.character.api.domain.entity.Character

internal fun CharacterListState.uploading(): CharacterListState {
    return copy(isLoadingMore = true)
}

internal fun CharacterListState.refresh(): CharacterListState {
    return copy(skeleton = true, error = false)
}

internal fun CharacterListState.failure(showErrorScreen: Boolean): CharacterListState {
    return copy(skeleton = false, isLoadingMore = false, error = showErrorScreen)
}

internal fun CharacterListState.success(characters: List<Character>): CharacterListState {
    return copy(skeleton = false, isLoadingMore = false, characters = characters)
}

internal fun CharacterListState.endReached(): CharacterListState {
    return copy(uploadAllCharacters = true)
}