package org.pet.project.rickandmorty.feature.character.impl.data.repository


import kotlinx.coroutines.flow.map
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Character
import org.pet.project.rickandmorty.feature.character.api.domain.repository.CharacterRepository
import org.pet.project.rickandmorty.feature.character.impl.data.datasource.RemoteCharacterDataSource
import org.pet.project.rickandmorty.feature.character.impl.data.mapper.toItem
import org.pet.project.rickandmorty.feature.character.impl.data.model.CharacterResponse
import org.pet.project.rickandmorty.feature.character.impl.data.paginator.CharacterPaginator
import org.pet.project.rickandmorty.library.result.Result
import org.pet.project.rickandmorty.library.result.map

internal class CharacterRepositoryImpl(
    private val remoteDataSource: RemoteCharacterDataSource,
    private val paginator: CharacterPaginator
) : CharacterRepository {

    override val characters = paginator.paginationFlow.map { it.toItem() }

    override suspend fun loadCharacterList() = paginator.loadItems()

    override suspend fun getCharacter(id: Int): Result<Character> {
        return remoteDataSource.getCharacter(id).map(CharacterResponse::toItem)
    }

    override suspend fun searchAllCharactersByName(name: String): Result<List<Character>> {
        return remoteDataSource.getAllCharactersByName(name).map { response ->
            response.map(CharacterResponse::toItem)
        }
    }
}