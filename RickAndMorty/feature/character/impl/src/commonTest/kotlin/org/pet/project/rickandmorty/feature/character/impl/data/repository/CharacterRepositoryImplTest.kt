package org.pet.project.rickandmorty.feature.character.impl.data.repository

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.pet.project.rickandmorty.common.data.InfoResponse
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Character
import org.pet.project.rickandmorty.feature.character.api.domain.entity.CharacterState
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Gender
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Status
import org.pet.project.rickandmorty.feature.character.impl.data.datasource.RemoteCharacterDataSource
import org.pet.project.rickandmorty.feature.character.impl.data.model.CharacterListResponse
import org.pet.project.rickandmorty.feature.character.impl.data.model.CharacterResponse
import org.pet.project.rickandmorty.feature.character.impl.data.model.LocationResponse
import org.pet.project.rickandmorty.feature.character.impl.data.model.OriginResponse
import org.pet.project.rickandmorty.feature.character.impl.data.paginator.CharacterPaginator
import org.pet.project.rickandmorty.library.result.Result
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class CharacterRepositoryImplTest {

    private lateinit var fakeDataSource: FakeRemoteCharacterDataSource
    private lateinit var paginator: CharacterPaginator
    private lateinit var repository: CharacterRepositoryImpl

    @BeforeTest
    fun setUp() {
        fakeDataSource = FakeRemoteCharacterDataSource()
        paginator = CharacterPaginator(fakeDataSource)
        repository = CharacterRepositoryImpl(fakeDataSource, paginator)
    }

    private fun createCharacterResponse(id: Int, name: String = "Rick Sanchez"): CharacterResponse {
        return CharacterResponse(
            id = id,
            name = name,
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            origin = OriginResponse("Earth (C-137)", ""),
            location = LocationResponse("Citadel of Ricks", ""),
            image = "https://rickandmortyapi.com/api/character/avatar/$id.jpeg",
            episode = listOf(
                "https://rickandmortyapi.com/api/episode/1",
                "https://rickandmortyapi.com/api/episode/2",
                "https://rickandmortyapi.com/api/episode/3"
            ),
            url = "https://rickandmortyapi.com/api/character/$id",
            created = "2017-11-04T18:48:46.250Z"
        )
    }

    // Characters flow tests

    @Test
    fun `characters flow emits Initial state initially EXPECT CharacterState Initial`() = runTest {
        val state = repository.characters.first()

        assertIs<CharacterState.Initial>(state)
    }

    @Test
    fun `characters flow after loadCharacterList success EXPECT CharacterState Success`() = runTest {
        fakeDataSource.setPageResponse(1, createSuccessPageResponse())

        repository.loadCharacterList()
        val state = repository.characters.first { it is CharacterState.Success }

        assertIs<CharacterState.Success>(state)
    }

    @Test
    fun `characters flow after loadCharacterList success EXPECT characters list not empty`() = runTest {
        fakeDataSource.setPageResponse(1, createSuccessPageResponse())

        repository.loadCharacterList()
        val state = repository.characters.first { it is CharacterState.Success }
        assertIs<CharacterState.Success>(state)

        assertTrue(state.value.isNotEmpty())
    }

    @Test
    fun `characters flow after loadCharacterList error EXPECT CharacterState Error`() = runTest {
        val exception = RuntimeException("Network error")
        fakeDataSource.setPageError(1, exception)

        repository.loadCharacterList()
        val state = repository.characters.first { it is CharacterState.Error }

        assertIs<CharacterState.Error>(state)
    }

    @Test
    fun `characters flow after loadCharacterList error EXPECT correct exception`() = runTest {
        val exception = RuntimeException("Network error")
        fakeDataSource.setPageError(1, exception)

        repository.loadCharacterList()
        val state = repository.characters.first { it is CharacterState.Error }
        assertIs<CharacterState.Error>(state)

        assertEquals(exception, state.throwable)
    }

    @Test
    fun `characters flow after reaching end EXPECT CharacterState End`() = runTest {
        val singlePageResponse = CharacterListResponse(
            info = InfoResponse(count = 1, pages = 1, next = null, prev = null),
            results = listOf(createCharacterResponse(1))
        )
        fakeDataSource.setPageResponse(1, Result.Success.Value(singlePageResponse))

        repository.loadCharacterList()
        val state = repository.characters.first { it is CharacterState.End }

        assertIs<CharacterState.End>(state)
    }

    // loadCharacterList tests

    @Test
    fun `loadCharacterList delegates to paginator EXPECT data source called`() = runTest {
        fakeDataSource.setPageResponse(1, createSuccessPageResponse())

        repository.loadCharacterList()

        assertEquals(1, fakeDataSource.getCharactersPageCallCount)
    }

    // getCharacter tests

    @Test
    fun `getCharacter with valid id EXPECT Result Success`() = runTest {
        fakeDataSource.setCharacterResponse(1, Result.Success.Value(createCharacterResponse(1)))

        val result = repository.getCharacter(1)

        assertIs<Result.Success<Character>>(result)
    }

    @Test
    fun `getCharacter with valid id EXPECT correct character id`() = runTest {
        fakeDataSource.setCharacterResponse(42, Result.Success.Value(createCharacterResponse(42)))

        val result = repository.getCharacter(42)
        val character = assertIs<Result.Success<Character>>(result).value

        assertEquals(42, character.id)
    }

    @Test
    fun `getCharacter with valid id EXPECT correct character name`() = runTest {
        fakeDataSource.setCharacterResponse(1, Result.Success.Value(createCharacterResponse(1, "Morty Smith")))

        val result = repository.getCharacter(1)
        val character = assertIs<Result.Success<Character>>(result).value

        assertEquals("Morty Smith", character.name)
    }

    @Test
    fun `getCharacter with valid id EXPECT correct gender mapping`() = runTest {
        fakeDataSource.setCharacterResponse(1, Result.Success.Value(createCharacterResponse(1)))

        val result = repository.getCharacter(1)
        val character = assertIs<Result.Success<Character>>(result).value

        assertEquals(Gender.MALE, character.gender)
    }

    @Test
    fun `getCharacter with valid id EXPECT correct status mapping`() = runTest {
        fakeDataSource.setCharacterResponse(1, Result.Success.Value(createCharacterResponse(1)))

        val result = repository.getCharacter(1)
        val character = assertIs<Result.Success<Character>>(result).value

        assertEquals(Status.ALIVE, character.status)
    }

    @Test
    fun `getCharacter with valid id EXPECT correct species`() = runTest {
        fakeDataSource.setCharacterResponse(1, Result.Success.Value(createCharacterResponse(1)))

        val result = repository.getCharacter(1)
        val character = assertIs<Result.Success<Character>>(result).value

        assertEquals("Human", character.species)
    }

    @Test
    fun `getCharacter with valid id EXPECT correct origin`() = runTest {
        fakeDataSource.setCharacterResponse(1, Result.Success.Value(createCharacterResponse(1)))

        val result = repository.getCharacter(1)
        val character = assertIs<Result.Success<Character>>(result).value

        assertEquals("Earth (C-137)", character.origin)
    }

    @Test
    fun `getCharacter with valid id EXPECT correct location`() = runTest {
        fakeDataSource.setCharacterResponse(1, Result.Success.Value(createCharacterResponse(1)))

        val result = repository.getCharacter(1)
        val character = assertIs<Result.Success<Character>>(result).value

        assertEquals("Citadel of Ricks", character.location)
    }

    @Test
    fun `getCharacter with valid id EXPECT correct image URL`() = runTest {
        fakeDataSource.setCharacterResponse(1, Result.Success.Value(createCharacterResponse(1)))

        val result = repository.getCharacter(1)
        val character = assertIs<Result.Success<Character>>(result).value

        assertEquals("https://rickandmortyapi.com/api/character/avatar/1.jpeg", character.image)
    }

    @Test
    fun `getCharacter with valid id EXPECT correct episodes list`() = runTest {
        fakeDataSource.setCharacterResponse(1, Result.Success.Value(createCharacterResponse(1)))

        val result = repository.getCharacter(1)
        val character = assertIs<Result.Success<Character>>(result).value

        assertEquals(listOf(1, 2, 3), character.episodes)
    }

    @Test
    fun `getCharacter with invalid id EXPECT Result Failure`() = runTest {
        val exception = RuntimeException("Character not found")
        fakeDataSource.setCharacterError(999, exception)

        val result = repository.getCharacter(999)

        assertIs<Result.Failure<*>>(result)
    }

    @Test
    fun `getCharacter with error EXPECT correct exception`() = runTest {
        val exception = RuntimeException("Character not found")
        fakeDataSource.setCharacterError(999, exception)

        val result = repository.getCharacter(999)
        val failure = assertIs<Result.Failure<*>>(result)

        assertEquals(exception, failure.error)
    }

    // searchAllCharactersByName tests

    @Test
    fun `searchAllCharactersByName with results EXPECT Result Success`() = runTest {
        val characters = listOf(createCharacterResponse(1), createCharacterResponse(2))
        fakeDataSource.setSearchResponse("Rick", Result.Success.Value(characters))

        val result = repository.searchAllCharactersByName("Rick")

        assertIs<Result.Success<List<Character>>>(result)
    }

    @Test
    fun `searchAllCharactersByName with results EXPECT correct list size`() = runTest {
        val characters = listOf(createCharacterResponse(1), createCharacterResponse(2))
        fakeDataSource.setSearchResponse("Rick", Result.Success.Value(characters))

        val result = repository.searchAllCharactersByName("Rick")
        val characterList = assertIs<Result.Success<List<Character>>>(result).value

        assertEquals(2, characterList.size)
    }

    @Test
    fun `searchAllCharactersByName with empty results EXPECT empty list`() = runTest {
        fakeDataSource.setSearchResponse("NonExistent", Result.Success.Value(emptyList()))

        val result = repository.searchAllCharactersByName("NonExistent")
        val characterList = assertIs<Result.Success<List<Character>>>(result).value

        assertTrue(characterList.isEmpty())
    }

    @Test
    fun `searchAllCharactersByName with error EXPECT Result Failure`() = runTest {
        val exception = RuntimeException("Search failed")
        fakeDataSource.setSearchError("Rick", exception)

        val result = repository.searchAllCharactersByName("Rick")

        assertIs<Result.Failure<*>>(result)
    }

    @Test
    fun `searchAllCharactersByName with error EXPECT correct exception`() = runTest {
        val exception = RuntimeException("Search failed")
        fakeDataSource.setSearchError("Rick", exception)

        val result = repository.searchAllCharactersByName("Rick")
        val failure = assertIs<Result.Failure<*>>(result)

        assertEquals(exception, failure.error)
    }

    private fun createSuccessPageResponse(): Result<CharacterListResponse> {
        return Result.Success.Value(
            CharacterListResponse(
                info = InfoResponse(count = 826, pages = 42, next = "https://example.com/page/2", prev = null),
                results = listOf(createCharacterResponse(1))
            )
        )
    }
}

private class FakeRemoteCharacterDataSource : RemoteCharacterDataSource {

    private val pageResponses = mutableMapOf<Int, Result<CharacterListResponse>>()
    private val characterResponses = mutableMapOf<Int, Result<CharacterResponse>>()
    private val searchResponses = mutableMapOf<String, Result<List<CharacterResponse>>>()

    var getCharactersPageCallCount = 0
        private set

    fun setPageResponse(page: Int, response: Result<CharacterListResponse>) {
        pageResponses[page] = response
    }

    fun setPageError(page: Int, error: Throwable) {
        pageResponses[page] = Result.Failure.Error(error)
    }

    fun setCharacterResponse(id: Int, response: Result<CharacterResponse>) {
        characterResponses[id] = response
    }

    fun setCharacterError(id: Int, error: Throwable) {
        characterResponses[id] = Result.Failure.Error(error)
    }

    fun setSearchResponse(name: String, response: Result<List<CharacterResponse>>) {
        searchResponses[name] = response
    }

    fun setSearchError(name: String, error: Throwable) {
        searchResponses[name] = Result.Failure.Error(error)
    }

    override suspend fun getCharactersPage(
        pageNumber: Int,
        params: Map<String, String>
    ): Result<CharacterListResponse> {
        getCharactersPageCallCount++
        return pageResponses[pageNumber]
            ?: Result.Failure.Error(IllegalStateException("No response configured for page $pageNumber"))
    }

    override suspend fun getCharacter(id: Int): Result<CharacterResponse> {
        return characterResponses[id]
            ?: Result.Failure.Error(IllegalStateException("No response configured for character $id"))
    }

    override suspend fun getAllCharactersByName(name: String): Result<List<CharacterResponse>> {
        return searchResponses[name]
            ?: Result.Failure.Error(IllegalStateException("No response configured for search $name"))
    }
}
