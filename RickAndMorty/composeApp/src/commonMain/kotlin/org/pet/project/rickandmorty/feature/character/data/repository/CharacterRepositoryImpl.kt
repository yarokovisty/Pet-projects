package org.pet.project.rickandmorty.feature.character.data.repository


import kotlinx.coroutines.flow.map
import org.pet.project.rickandmorty.core.result.Result
import org.pet.project.rickandmorty.core.result.map
import org.pet.project.rickandmorty.feature.character.data.datasource.RemoteCharacterDataSource
import org.pet.project.rickandmorty.feature.character.data.mapper.toItem
import org.pet.project.rickandmorty.feature.character.data.paginator.CharacterPaginator
import org.pet.project.rickandmorty.feature.character.domain.entity.Character
import org.pet.project.rickandmorty.feature.character.domain.repository.CharacterRepository


internal class CharacterRepositoryImpl(
    private val remoteDataSource: RemoteCharacterDataSource,
    private val paginator: CharacterPaginator
) : CharacterRepository {

    override val characters = paginator.paginationFlow.map { result ->
        result.map { list ->
            val characters = list.results.map {  character -> character.toItem() }
            characters
        }
    }

    override suspend fun loadCharacterList() {
        paginator.loadItems()
    }

    override suspend fun getCharacter(id: Int): Result<Character> {
        val result = remoteDataSource
            .getCharacter(id)
            .map { it.toItem() }
        return result
    }
}