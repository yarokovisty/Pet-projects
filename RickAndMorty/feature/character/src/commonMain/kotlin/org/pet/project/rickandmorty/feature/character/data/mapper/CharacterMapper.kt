package org.pet.project.rickandmorty.feature.character.data.mapper

import org.pet.project.rickandmorty.common.data.PaginatorState
import org.pet.project.rickandmorty.feature.character.data.model.CharacterListResponse
import org.pet.project.rickandmorty.feature.character.data.model.CharacterResponse
import org.pet.project.rickandmorty.feature.character.domain.entity.Character
import org.pet.project.rickandmorty.feature.character.domain.entity.CharacterState
import org.pet.project.rickandmorty.feature.character.domain.entity.Gender
import org.pet.project.rickandmorty.feature.character.domain.entity.Status

internal fun CharacterResponse.toItem(): Character {
	return Character(
		id = this.id,
		name = this.name,
		image = this.image,
		gender = this.gender.toGender(),
		status = this.status.toStatus(),
		species = this.species,
		origin = this.origin.name,
		location = this.location.name
	)
}

private fun String.toGender(): Gender {
	return when(this) {
		"Female" -> Gender.FEMALE
		"Male" -> Gender.MALE
		"Genderless" -> Gender.GENDERLESS
		else -> Gender.UNKNOWN
	}
}

private fun String.toStatus(): Status {
	return when(this) {
		"Alive" -> Status.ALIVE
		"Dead" -> Status.DEAD
		else -> Status.UNKNOWN
	}
}

internal fun PaginatorState<CharacterListResponse>.toItem(): CharacterState {
	return when(this) {
        PaginatorState.End -> CharacterState.End
        is PaginatorState.Error -> CharacterState.Error(this.throwable)
        PaginatorState.Initial -> CharacterState.Initial
        PaginatorState.Loading -> CharacterState.Loading
        is PaginatorState.Success<CharacterListResponse> -> {
			val characters = value.results.map(CharacterResponse::toItem)
			CharacterState.Success(characters)
		}
    }
}