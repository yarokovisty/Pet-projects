package org.pet.project.rickandmorty.feature.character.impl.data.datasource

import org.pet.project.rickandmorty.feature.character.impl.data.model.CharacterListResponse
import org.pet.project.rickandmorty.feature.character.impl.data.model.CharacterResponse
import org.pet.project.rickandmorty.library.result.Result


internal interface RemoteCharacterDataSource {

    suspend fun getCharactersPage(page: Int): Result<CharacterListResponse>

    suspend fun getCharacter(id: Int): Result<CharacterResponse>

    suspend fun getCharactersByName(name: String): Result<CharacterListResponse>
}