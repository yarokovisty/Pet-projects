package org.pet.project.rickandmorty.feature.character.api.domain.entity

sealed interface CharacterState {
    object Initial : CharacterState
    class Success(val value: List<Character>) : CharacterState
    class Error(val throwable: Throwable) : CharacterState
    object Loading : CharacterState
    object End : CharacterState
}