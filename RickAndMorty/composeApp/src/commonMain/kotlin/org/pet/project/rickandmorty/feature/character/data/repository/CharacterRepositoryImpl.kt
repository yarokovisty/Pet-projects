package org.pet.project.rickandmorty.feature.character.data.repository


import kotlinx.coroutines.flow.map
import org.pet.project.rickandmorty.common.utils.map
import org.pet.project.rickandmorty.feature.character.data.model.toItem
import org.pet.project.rickandmorty.feature.character.data.paginator.CharacterPaginator
import org.pet.project.rickandmorty.feature.character.domain.repository.CharacterRepository


internal class CharacterRepositoryImpl(
    private val paginator: CharacterPaginator
) : CharacterRepository {

    override val characters = paginator.paginationFlow.map { result ->
        result.map { characterListResponse ->
            val characters = characterListResponse.results.map {  characterResponse ->
                characterResponse.toItem()
            }
            characters
        }
    }

    override suspend fun loadCharacterList() {
        paginator.loadItems()
    }
}