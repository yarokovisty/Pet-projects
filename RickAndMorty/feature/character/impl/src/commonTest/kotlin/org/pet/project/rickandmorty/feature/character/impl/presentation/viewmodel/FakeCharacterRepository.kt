package org.pet.project.rickandmorty.feature.character.impl.presentation.viewmodel

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Character
import org.pet.project.rickandmorty.feature.character.api.domain.entity.CharacterState
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Gender
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Status
import org.pet.project.rickandmorty.feature.character.api.domain.repository.CharacterRepository
import org.pet.project.rickandmorty.library.result.Result

internal class FakeCharacterRepository : CharacterRepository {

    private val _characters = MutableSharedFlow<CharacterState>(replay = 1)
    override val characters: Flow<CharacterState> = _characters

    var loadCharacterListCallCount = 0
    var getCharacterCallCount = 0
    var searchAllCharactersByNameCallCount = 0

    var getCharacterResult: Result<Character> = Result.Success.Value(createTestCharacter())
    var searchAllCharactersByNameResult: Result<List<Character>> = Result.Success.Value(emptyList())

    suspend fun emitCharacterState(state: CharacterState) {
        _characters.emit(state)
    }

    override suspend fun loadCharacterList() {
        loadCharacterListCallCount++
    }

    override suspend fun getCharacter(id: Int): Result<Character> {
        getCharacterCallCount++
        return getCharacterResult
    }

    override suspend fun searchAllCharactersByName(name: String): Result<List<Character>> {
        searchAllCharactersByNameCallCount++
        return searchAllCharactersByNameResult
    }

    companion object {
        fun createTestCharacter(
            id: Int = 1,
            name: String = "Rick Sanchez",
            gender: Gender = Gender.MALE,
            status: Status = Status.ALIVE
        ) = Character(
            id = id,
            name = name,
            image = "https://rickandmortyapi.com/api/character/avatar/$id.jpeg",
            gender = gender,
            status = status,
            species = "Human",
            origin = "Earth",
            location = "Earth",
            episodes = listOf(1, 2, 3)
        )

        fun createTestCharacterList(count: Int = 3): List<Character> =
            (1..count).map { createTestCharacter(id = it, name = "Character $it") }
    }
}
