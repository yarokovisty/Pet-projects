package org.pet.project.rickandmorty.feature.character.impl.data.datasource

import org.pet.project.rickandmorty.feature.character.impl.data.model.CharacterListResponse
import org.pet.project.rickandmorty.feature.character.impl.data.model.CharacterResponse
import org.pet.project.rickandmorty.library.result.Result


internal interface RemoteCharacterDataSource {
    suspend fun getCharactersPage(
        pageNumber: Int,
        params: Map<String, String> = emptyMap()
    ): Result<CharacterListResponse>

    suspend fun getCharacter(id: Int): Result<CharacterResponse>

    suspend fun getAllCharactersByName(name: String): Result<List<CharacterResponse>>
}