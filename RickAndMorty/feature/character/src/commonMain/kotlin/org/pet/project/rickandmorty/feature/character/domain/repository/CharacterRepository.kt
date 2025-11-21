package org.pet.project.rickandmorty.feature.character.domain.repository

import kotlinx.coroutines.flow.Flow
import org.pet.project.rickandmorty.feature.character.domain.entity.Character
import org.pet.project.rickandmorty.feature.character.domain.entity.CharacterState
import org.pet.project.rickandmorty.library.result.Result

interface CharacterRepository {

    val characters: Flow<CharacterState>

    suspend fun loadCharacterList()

    suspend fun getCharacter(id: Int): Result<Character>
}