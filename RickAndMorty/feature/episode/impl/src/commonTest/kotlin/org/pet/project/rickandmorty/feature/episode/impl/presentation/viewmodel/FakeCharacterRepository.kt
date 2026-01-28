package org.pet.project.rickandmorty.feature.episode.impl.presentation.viewmodel

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

    var getCharacterCallCount = 0
        private set

    var getCharacterResult: Result<Character> = Result.Success.Value(createTestCharacter())
    var shouldFail: Boolean = false

    override suspend fun loadCharacterList() {
        // No-op for test
    }

    override suspend fun getCharacter(id: Int): Result<Character> {
        getCharacterCallCount++
        return getCharacterResult
    }

    override suspend fun searchAllCharactersByName(name: String): Result<List<Character>> {
        return Result.Success.Value(emptyList())
    }

    companion object {
        fun createTestCharacter(
            id: Int = 1,
            name: String = "Rick Sanchez",
            image: String = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            gender: Gender = Gender.MALE,
            status: Status = Status.ALIVE,
            species: String = "Human",
            origin: String = "Earth (C-137)",
            location: String = "Citadel of Ricks",
            episodes: List<Int> = listOf(1, 2, 3)
        ) = Character(
            id = id,
            name = name,
            image = image,
            gender = gender,
            status = status,
            species = species,
            origin = origin,
            location = location,
            episodes = episodes
        )
    }
}
