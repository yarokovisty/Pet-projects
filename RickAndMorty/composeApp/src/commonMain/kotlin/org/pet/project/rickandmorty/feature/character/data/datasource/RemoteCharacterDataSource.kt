package org.pet.project.rickandmorty.feature.character.data.datasource

import org.pet.project.rickandmorty.core.result.Result
import org.pet.project.rickandmorty.feature.character.data.model.CharacterListResponse

internal interface RemoteCharacterDataSource {

    suspend fun getCharactersPage(page: Int): Result<CharacterListResponse>
}