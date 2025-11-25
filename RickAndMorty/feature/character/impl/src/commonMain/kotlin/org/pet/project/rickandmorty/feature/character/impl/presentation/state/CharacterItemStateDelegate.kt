package org.pet.project.rickandmorty.feature.character.impl.presentation.state

import org.pet.project.rickandmorty.feature.character.api.domain.entity.Character

internal fun CharacterItemState.loading(): CharacterItemState {
    return copy(loading = true, error = false)
}

internal fun CharacterItemState.success(character: Character): CharacterItemState {
    return copy(loading = false, error = false, character = character)
}

internal fun CharacterItemState.error(): CharacterItemState {
    return copy(loading = false, error = true)
}