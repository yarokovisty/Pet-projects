package org.pet.project.rickandmorty.feature.character.data.datasource

import org.pet.project.rickandmorty.core.result.Result
import org.pet.project.rickandmorty.feature.character.data.model.CharacterListResponse
import org.pet.project.rickandmorty.feature.character.data.model.CharacterResponse

internal interface RemoteCharacterDataSource {

    suspend fun getCharactersPage(page: Int): Result<CharacterListResponse>

    suspend fun getCharacter(id: Int): Result<CharacterResponse>
}