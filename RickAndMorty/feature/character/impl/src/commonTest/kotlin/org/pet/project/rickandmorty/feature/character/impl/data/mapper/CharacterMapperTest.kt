package org.pet.project.rickandmorty.feature.character.impl.data.mapper

import org.pet.project.rickandmorty.common.data.InfoResponse
import org.pet.project.rickandmorty.common.data.PaginatorState
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Character
import org.pet.project.rickandmorty.feature.character.api.domain.entity.CharacterState
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Gender
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Status
import org.pet.project.rickandmorty.feature.character.impl.data.model.CharacterListResponse
import org.pet.project.rickandmorty.feature.character.impl.data.model.CharacterResponse
import org.pet.project.rickandmorty.feature.character.impl.data.model.LocationResponse
import org.pet.project.rickandmorty.feature.character.impl.data.model.OriginResponse
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class CharacterMapperTest {

    @Test
    fun `map CharacterResponse to Character EXPECT correct Character entity`() {
        val response = createCharacterResponse(
            id = 1,
            name = "Rick Sanchez",
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            gender = "Male",
            status = "Alive",
            species = "Human",
            originName = "Earth (C-137)",
            locationName = "Citadel of Ricks",
            episode = listOf(
                "https://rickandmortyapi.com/api/episode/1",
                "https://rickandmortyapi.com/api/episode/2"
            )
        )
        val expected = Character(
            id = 1,
            name = "Rick Sanchez",
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            gender = Gender.MALE,
            status = Status.ALIVE,
            species = "Human",
            origin = "Earth (C-137)",
            location = "Citadel of Ricks",
            episodes = listOf(1, 2)
        )

        val character = response.toItem()

        assertEquals(expected, character)
    }

    @Test
    fun `map CharacterResponse with Female gender EXPECT Gender FEMALE`() {
        val response = createCharacterResponse(gender = "Female")

        val character = response.toItem()

        assertEquals(Gender.FEMALE, character.gender)
    }

    @Test
    fun `map CharacterResponse with Male gender EXPECT Gender MALE`() {
        val response = createCharacterResponse(gender = "Male")

        val character = response.toItem()

        assertEquals(Gender.MALE, character.gender)
    }

    @Test
    fun `map CharacterResponse with Genderless gender EXPECT Gender GENDERLESS`() {
        val response = createCharacterResponse(gender = "Genderless")

        val character = response.toItem()

        assertEquals(Gender.GENDERLESS, character.gender)
    }

    @Test
    fun `map CharacterResponse with unknown gender string EXPECT Gender UNKNOWN`() {
        val response = createCharacterResponse(gender = "unknown")

        val character = response.toItem()

        assertEquals(Gender.UNKNOWN, character.gender)
    }

    @Test
    fun `map CharacterResponse with invalid gender EXPECT Gender UNKNOWN`() {
        val response = createCharacterResponse(gender = "InvalidGender")

        val character = response.toItem()

        assertEquals(Gender.UNKNOWN, character.gender)
    }

    @Test
    fun `map CharacterResponse with empty gender string EXPECT Gender UNKNOWN`() {
        val response = createCharacterResponse(gender = "")

        val character = response.toItem()

        assertEquals(Gender.UNKNOWN, character.gender)
    }

    @Test
    fun `map CharacterResponse with Alive status EXPECT Status ALIVE`() {
        val response = createCharacterResponse(status = "Alive")

        val character = response.toItem()

        assertEquals(Status.ALIVE, character.status)
    }

    @Test
    fun `map CharacterResponse with Dead status EXPECT Status DEAD`() {
        val response = createCharacterResponse(status = "Dead")

        val character = response.toItem()

        assertEquals(Status.DEAD, character.status)
    }

    @Test
    fun `map CharacterResponse with unknown status string EXPECT Status UNKNOWN`() {
        val response = createCharacterResponse(status = "unknown")

        val character = response.toItem()

        assertEquals(Status.UNKNOWN, character.status)
    }

    @Test
    fun `map CharacterResponse with invalid status EXPECT Status UNKNOWN`() {
        val response = createCharacterResponse(status = "InvalidStatus")

        val character = response.toItem()

        assertEquals(Status.UNKNOWN, character.status)
    }

    @Test
    fun `map CharacterResponse with empty status string EXPECT Status UNKNOWN`() {
        val response = createCharacterResponse(status = "")

        val character = response.toItem()

        assertEquals(Status.UNKNOWN, character.status)
    }

    @Test
    fun `map CharacterResponse with episode URLs EXPECT correct episode IDs extracted`() {
        val response = createCharacterResponse(
            episode = listOf(
                "https://rickandmortyapi.com/api/episode/1",
                "https://rickandmortyapi.com/api/episode/10",
                "https://rickandmortyapi.com/api/episode/100"
            )
        )

        val character = response.toItem()

        assertEquals(listOf(1, 10, 100), character.episodes)
    }

    @Test
    fun `map CharacterResponse with empty episode list EXPECT empty episodes`() {
        val response = createCharacterResponse(episode = emptyList())

        val character = response.toItem()

        assertTrue(character.episodes.isEmpty())
    }

    @Test
    fun `map CharacterResponse with single episode URL EXPECT single episode ID`() {
        val response = createCharacterResponse(
            episode = listOf("https://rickandmortyapi.com/api/episode/42")
        )

        val character = response.toItem()

        assertEquals(listOf(42), character.episodes)
    }

    @Test
    fun `map PaginatorState Initial EXPECT CharacterState Initial`() {
        val paginatorState: PaginatorState<CharacterListResponse> = PaginatorState.Initial

        val characterState = paginatorState.toItem()

        assertIs<CharacterState.Initial>(characterState)
    }

    @Test
    fun `map PaginatorState Loading EXPECT CharacterState Loading`() {
        val paginatorState: PaginatorState<CharacterListResponse> = PaginatorState.Loading

        val characterState = paginatorState.toItem()

        assertIs<CharacterState.Loading>(characterState)
    }

    @Test
    fun `map PaginatorState End EXPECT CharacterState End`() {
        val paginatorState: PaginatorState<CharacterListResponse> = PaginatorState.End

        val characterState = paginatorState.toItem()

        assertIs<CharacterState.End>(characterState)
    }

    @Test
    fun `map PaginatorState Error EXPECT CharacterState Error`() {
        val exception = RuntimeException("Test error")
        val paginatorState: PaginatorState<CharacterListResponse> = PaginatorState.Error(exception)

        val characterState = paginatorState.toItem()

        assertIs<CharacterState.Error>(characterState)
    }

    @Test
    fun `map PaginatorState Error EXPECT same throwable preserved`() {
        val exception = RuntimeException("Test error")
        val paginatorState: PaginatorState<CharacterListResponse> = PaginatorState.Error(exception)

        val characterState = paginatorState.toItem() as CharacterState.Error

        assertEquals(exception, characterState.throwable)
    }

    @Test
    fun `map PaginatorState Error with message EXPECT CharacterState Error`() {
        val errorMessage = "Network connection failed"
        val exception = IllegalStateException(errorMessage)
        val paginatorState: PaginatorState<CharacterListResponse> = PaginatorState.Error(exception)

        val characterState = paginatorState.toItem()

        assertIs<CharacterState.Error>(characterState)
    }

    @Test
    fun `map PaginatorState Error with message EXPECT message preserved`() {
        val errorMessage = "Network connection failed"
        val exception = IllegalStateException(errorMessage)
        val paginatorState: PaginatorState<CharacterListResponse> = PaginatorState.Error(exception)

        val characterState = paginatorState.toItem() as CharacterState.Error

        assertEquals(errorMessage, characterState.throwable.message)
    }

    @Test
    fun `map PaginatorState Success with characters EXPECT CharacterState Success`() {
        val characterResponses = listOf(
            createCharacterResponse(id = 1, name = "Rick"),
            createCharacterResponse(id = 2, name = "Morty")
        )
        val listResponse = CharacterListResponse(
            info = createInfoResponse(),
            results = characterResponses
        )
        val paginatorState: PaginatorState<CharacterListResponse> = PaginatorState.Success(listResponse)

        val characterState = paginatorState.toItem()

        assertIs<CharacterState.Success>(characterState)
    }

    @Test
    fun `map PaginatorState Success with characters EXPECT correct size`() {
        val characterResponses = listOf(
            createCharacterResponse(id = 1, name = "Rick"),
            createCharacterResponse(id = 2, name = "Morty")
        )
        val listResponse = CharacterListResponse(
            info = createInfoResponse(),
            results = characterResponses
        )
        val paginatorState: PaginatorState<CharacterListResponse> = PaginatorState.Success(listResponse)

        val characterState = paginatorState.toItem() as CharacterState.Success

        assertEquals(2, characterState.value.size)
    }

    @Test
    fun `map PaginatorState Success with characters EXPECT first character id correct`() {
        val characterResponses = listOf(
            createCharacterResponse(id = 1, name = "Rick"),
            createCharacterResponse(id = 2, name = "Morty")
        )
        val listResponse = CharacterListResponse(
            info = createInfoResponse(),
            results = characterResponses
        )
        val paginatorState: PaginatorState<CharacterListResponse> = PaginatorState.Success(listResponse)

        val characterState = paginatorState.toItem() as CharacterState.Success

        assertEquals(1, characterState.value[0].id)
    }

    @Test
    fun `map PaginatorState Success with characters EXPECT first character name correct`() {
        val characterResponses = listOf(
            createCharacterResponse(id = 1, name = "Rick"),
            createCharacterResponse(id = 2, name = "Morty")
        )
        val listResponse = CharacterListResponse(
            info = createInfoResponse(),
            results = characterResponses
        )
        val paginatorState: PaginatorState<CharacterListResponse> = PaginatorState.Success(listResponse)

        val characterState = paginatorState.toItem() as CharacterState.Success

        assertEquals("Rick", characterState.value[0].name)
    }

    @Test
    fun `map PaginatorState Success with characters EXPECT second character id correct`() {
        val characterResponses = listOf(
            createCharacterResponse(id = 1, name = "Rick"),
            createCharacterResponse(id = 2, name = "Morty")
        )
        val listResponse = CharacterListResponse(
            info = createInfoResponse(),
            results = characterResponses
        )
        val paginatorState: PaginatorState<CharacterListResponse> = PaginatorState.Success(listResponse)

        val characterState = paginatorState.toItem() as CharacterState.Success

        assertEquals(2, characterState.value[1].id)
    }

    @Test
    fun `map PaginatorState Success with characters EXPECT second character name correct`() {
        val characterResponses = listOf(
            createCharacterResponse(id = 1, name = "Rick"),
            createCharacterResponse(id = 2, name = "Morty")
        )
        val listResponse = CharacterListResponse(
            info = createInfoResponse(),
            results = characterResponses
        )
        val paginatorState: PaginatorState<CharacterListResponse> = PaginatorState.Success(listResponse)

        val characterState = paginatorState.toItem() as CharacterState.Success

        assertEquals("Morty", characterState.value[1].name)
    }

    @Test
    fun `map PaginatorState Success with empty results EXPECT CharacterState Success`() {
        val listResponse = CharacterListResponse(
            info = createInfoResponse(count = 0, pages = 0),
            results = emptyList()
        )
        val paginatorState: PaginatorState<CharacterListResponse> = PaginatorState.Success(listResponse)

        val characterState = paginatorState.toItem()

        assertIs<CharacterState.Success>(characterState)
    }

    @Test
    fun `map PaginatorState Success with empty results EXPECT empty list`() {
        val listResponse = CharacterListResponse(
            info = createInfoResponse(count = 0, pages = 0),
            results = emptyList()
        )
        val paginatorState: PaginatorState<CharacterListResponse> = PaginatorState.Success(listResponse)

        val characterState = paginatorState.toItem() as CharacterState.Success

        assertTrue(characterState.value.isEmpty())
    }

    @Test
    fun `map PaginatorState Success with multiple characters EXPECT CharacterState Success`() {
        val characterResponses = listOf(
            createCharacterResponse(id = 1, name = "Rick", gender = "Male", status = "Alive"),
            createCharacterResponse(id = 2, name = "Morty", gender = "Male", status = "Alive"),
            createCharacterResponse(id = 3, name = "Summer", gender = "Female", status = "Alive"),
            createCharacterResponse(id = 4, name = "Birdperson", gender = "Male", status = "Dead")
        )
        val listResponse = CharacterListResponse(
            info = createInfoResponse(count = 4),
            results = characterResponses
        )
        val paginatorState: PaginatorState<CharacterListResponse> = PaginatorState.Success(listResponse)

        val characterState = paginatorState.toItem()

        assertIs<CharacterState.Success>(characterState)
    }

    @Test
    fun `map PaginatorState Success with multiple characters EXPECT correct size`() {
        val characterResponses = listOf(
            createCharacterResponse(id = 1, name = "Rick", gender = "Male", status = "Alive"),
            createCharacterResponse(id = 2, name = "Morty", gender = "Male", status = "Alive"),
            createCharacterResponse(id = 3, name = "Summer", gender = "Female", status = "Alive"),
            createCharacterResponse(id = 4, name = "Birdperson", gender = "Male", status = "Dead")
        )
        val listResponse = CharacterListResponse(
            info = createInfoResponse(count = 4),
            results = characterResponses
        )
        val paginatorState: PaginatorState<CharacterListResponse> = PaginatorState.Success(listResponse)

        val characterState = paginatorState.toItem() as CharacterState.Success

        assertEquals(4, characterState.value.size)
    }

    @Test
    fun `map PaginatorState Success with multiple characters EXPECT third character gender Female`() {
        val characterResponses = listOf(
            createCharacterResponse(id = 1, name = "Rick", gender = "Male", status = "Alive"),
            createCharacterResponse(id = 2, name = "Morty", gender = "Male", status = "Alive"),
            createCharacterResponse(id = 3, name = "Summer", gender = "Female", status = "Alive"),
            createCharacterResponse(id = 4, name = "Birdperson", gender = "Male", status = "Dead")
        )
        val listResponse = CharacterListResponse(
            info = createInfoResponse(count = 4),
            results = characterResponses
        )
        val paginatorState: PaginatorState<CharacterListResponse> = PaginatorState.Success(listResponse)

        val characterState = paginatorState.toItem() as CharacterState.Success

        assertEquals(Gender.FEMALE, characterState.value[2].gender)
    }

    @Test
    fun `map PaginatorState Success with multiple characters EXPECT fourth character status Dead`() {
        val characterResponses = listOf(
            createCharacterResponse(id = 1, name = "Rick", gender = "Male", status = "Alive"),
            createCharacterResponse(id = 2, name = "Morty", gender = "Male", status = "Alive"),
            createCharacterResponse(id = 3, name = "Summer", gender = "Female", status = "Alive"),
            createCharacterResponse(id = 4, name = "Birdperson", gender = "Male", status = "Dead")
        )
        val listResponse = CharacterListResponse(
            info = createInfoResponse(count = 4),
            results = characterResponses
        )
        val paginatorState: PaginatorState<CharacterListResponse> = PaginatorState.Success(listResponse)

        val characterState = paginatorState.toItem() as CharacterState.Success

        assertEquals(Status.DEAD, characterState.value[3].status)
    }

    private fun createCharacterResponse(
        id: Int = 1,
        name: String = "Test Character",
        status: String = "Alive",
        species: String = "Human",
        gender: String = "Male",
        originName: String = "Earth",
        locationName: String = "Earth",
        image: String = "https://example.com/image.png",
        episode: List<String> = listOf("https://rickandmortyapi.com/api/episode/1")
    ): CharacterResponse {
        return CharacterResponse(
            id = id,
            name = name,
            status = status,
            species = species,
            type = "",
            gender = gender,
            origin = OriginResponse(name = originName, url = ""),
            location = LocationResponse(name = locationName, url = ""),
            image = image,
            episode = episode,
            url = "",
            created = ""
        )
    }

    private fun createInfoResponse(
        count: Int = 826,
        pages: Int = 42,
        next: String? = "https://rickandmortyapi.com/api/character?page=2",
        prev: String? = null
    ): InfoResponse {
        return InfoResponse(
            count = count,
            pages = pages,
            next = next,
            prev = prev
        )
    }
}