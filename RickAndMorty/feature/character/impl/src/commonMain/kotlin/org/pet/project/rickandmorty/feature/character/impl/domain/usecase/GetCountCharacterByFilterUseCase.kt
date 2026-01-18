package org.pet.project.rickandmorty.feature.character.impl.domain.usecase

import org.pet.project.rickandmorty.feature.character.api.domain.entity.Character
import org.pet.project.rickandmorty.feature.character.api.domain.entity.CharacterFilter
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Gender
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Status

class GetCountCharacterByFilterUseCase {

    operator fun invoke(characters: List<Character>): Map<CharacterFilter, Int> {
        val genderCount = characters.groupingBy { it.gender }.eachCount()
        val statusCount = characters.groupingBy { it.status }.eachCount()

        return buildMap {
            Gender.entries.forEach { gender ->
                put(gender, genderCount[gender] ?: 0)
            }

            Status.entries.forEach { status ->
                put(status, statusCount[status] ?: 0)
            }
        }
    }
}
