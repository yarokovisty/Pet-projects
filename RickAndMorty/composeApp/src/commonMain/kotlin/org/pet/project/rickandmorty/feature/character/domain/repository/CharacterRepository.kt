package org.pet.project.rickandmorty.feature.character.domain.repository

import kotlinx.coroutines.flow.Flow
import org.pet.project.rickandmorty.core.result.Result
import org.pet.project.rickandmorty.feature.character.domain.entity.Character

internal interface CharacterRepository {

    val characters: Flow<Result<List<Character>>>

    suspend fun loadCharacterList()
}