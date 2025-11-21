package org.pet.project.rickandmorty.feature.character.data.repository


import kotlinx.coroutines.flow.map
import org.pet.project.rickandmorty.feature.character.data.datasource.RemoteCharacterDataSource
import org.pet.project.rickandmorty.feature.character.data.mapper.toItem
import org.pet.project.rickandmorty.feature.character.data.paginator.CharacterPaginator
import org.pet.project.rickandmorty.feature.character.domain.entity.Character
import org.pet.project.rickandmorty.feature.character.domain.repository.CharacterRepository
import org.pet.project.rickandmorty.library.result.Result
import org.pet.project.rickandmorty.library.result.map


internal class CharacterRepositoryImpl(
    private val remoteDataSource: RemoteCharacterDataSource,
    private val paginator: CharacterPaginator
) : CharacterRepository {

    override val characters = paginator.paginationFlow.map { it.toItem() }

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