package org.pet.project.rickandmorty.feature.character.impl.domain.usecase

import org.pet.project.rickandmorty.feature.character.api.domain.entity.Character
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Gender
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Status
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetCountCharacterByFilterUseCaseTest {

    private lateinit var useCase: GetCountCharacterByFilterUseCase
    private lateinit var testCharacters: List<Character>

    @BeforeTest
    fun setUp() {
        useCase = GetCountCharacterByFilterUseCase()
        testCharacters = createTestCharacters()
    }

    @Test
    fun `invoke with empty list EXPECT male count is zero`() {
        val result = useCase(emptyList())

        assertEquals(0, result[Gender.MALE])
    }

    @Test
    fun `invoke with empty list EXPECT female count is zero`() {
        val result = useCase(emptyList())

        assertEquals(0, result[Gender.FEMALE])
    }

    @Test
    fun `invoke with empty list EXPECT genderless count is zero`() {
        val result = useCase(emptyList())

        assertEquals(0, result[Gender.GENDERLESS])
    }

    @Test
    fun `invoke with empty list EXPECT unknown gender count is zero`() {
        val result = useCase(emptyList())

        assertEquals(0, result[Gender.UNKNOWN])
    }

    @Test
    fun `invoke with empty list EXPECT alive count is zero`() {
        val result = useCase(emptyList())

        assertEquals(0, result[Status.ALIVE])
    }

    @Test
    fun `invoke with empty list EXPECT dead count is zero`() {
        val result = useCase(emptyList())

        assertEquals(0, result[Status.DEAD])
    }

    @Test
    fun `invoke with empty list EXPECT unknown status count is zero`() {
        val result = useCase(emptyList())

        assertEquals(0, result[Status.UNKNOWN])
    }

    @Test
    fun `invoke with single male character EXPECT male count is one`() {
        val characters = listOf(createCharacter(Gender.MALE, Status.ALIVE))

        val result = useCase(characters)

        assertEquals(1, result[Gender.MALE])
    }

    @Test
    fun `invoke with single female character EXPECT female count is one`() {
        val characters = listOf(createCharacter(Gender.FEMALE, Status.ALIVE))

        val result = useCase(characters)

        assertEquals(1, result[Gender.FEMALE])
    }

    @Test
    fun `invoke with single alive character EXPECT alive count is one`() {
        val characters = listOf(createCharacter(Gender.MALE, Status.ALIVE))

        val result = useCase(characters)

        assertEquals(1, result[Status.ALIVE])
    }

    @Test
    fun `invoke with single dead character EXPECT dead count is one`() {
        val characters = listOf(createCharacter(Gender.MALE, Status.DEAD))

        val result = useCase(characters)

        assertEquals(1, result[Status.DEAD])
    }

    @Test
    fun `invoke with multiple characters of same gender EXPECT correct count`() {
        val characters = listOf(
            createCharacter(Gender.MALE, Status.ALIVE),
            createCharacter(Gender.MALE, Status.DEAD),
            createCharacter(Gender.MALE, Status.UNKNOWN)
        )

        val result = useCase(characters)

        assertEquals(3, result[Gender.MALE])
    }

    @Test
    fun `invoke with multiple characters of same status EXPECT correct count`() {
        val characters = listOf(
            createCharacter(Gender.MALE, Status.ALIVE),
            createCharacter(Gender.FEMALE, Status.ALIVE),
            createCharacter(Gender.GENDERLESS, Status.ALIVE)
        )

        val result = useCase(characters)

        assertEquals(3, result[Status.ALIVE])
    }

    @Test
    fun `invoke with test data EXPECT correct male count`() {
        val result = useCase(testCharacters)

        assertEquals(3, result[Gender.MALE])
    }

    @Test
    fun `invoke with test data EXPECT correct female count`() {
        val result = useCase(testCharacters)

        assertEquals(3, result[Gender.FEMALE])
    }

    @Test
    fun `invoke with test data EXPECT correct genderless count`() {
        val result = useCase(testCharacters)

        assertEquals(1, result[Gender.GENDERLESS])
    }

    @Test
    fun `invoke with test data EXPECT correct unknown gender count`() {
        val result = useCase(testCharacters)

        assertEquals(1, result[Gender.UNKNOWN])
    }

    @Test
    fun `invoke with test data EXPECT correct alive count`() {
        val result = useCase(testCharacters)

        assertEquals(5, result[Status.ALIVE])
    }

    @Test
    fun `invoke with test data EXPECT correct dead count`() {
        val result = useCase(testCharacters)

        assertEquals(2, result[Status.DEAD])
    }

    @Test
    fun `invoke with test data EXPECT correct unknown status count`() {
        val result = useCase(testCharacters)

        assertEquals(1, result[Status.UNKNOWN])
    }

    @Test
    fun `invoke with any input EXPECT all gender enum values present in result`() {
        val result = useCase(testCharacters)

        assertTrue(Gender.entries.all { result.containsKey(it) })
    }

    @Test
    fun `invoke with any input EXPECT all status enum values present in result`() {
        val result = useCase(testCharacters)

        assertTrue(Status.entries.all { result.containsKey(it) })
    }

    @Test
    fun `invoke with mixed characters EXPECT sum of gender counts equals total characters`() {
        val result = useCase(testCharacters)
        val totalGenderCount = Gender.entries.sumOf { result[it] ?: 0 }

        assertEquals(testCharacters.size, totalGenderCount)
    }

    @Test
    fun `invoke with mixed characters EXPECT sum of status counts equals total characters`() {
        val result = useCase(testCharacters)
        val totalStatusCount = Status.entries.sumOf { result[it] ?: 0 }

        assertEquals(testCharacters.size, totalStatusCount)
    }

    @Test
    fun `invoke with single genderless character EXPECT genderless count is one`() {
        val characters = listOf(createCharacter(Gender.GENDERLESS, Status.ALIVE))

        val result = useCase(characters)

        assertEquals(1, result[Gender.GENDERLESS])
    }

    @Test
    fun `invoke with single unknown gender character EXPECT unknown gender count is one`() {
        val characters = listOf(createCharacter(Gender.UNKNOWN, Status.ALIVE))

        val result = useCase(characters)

        assertEquals(1, result[Gender.UNKNOWN])
    }

    @Test
    fun `invoke with single unknown status character EXPECT unknown status count is one`() {
        val characters = listOf(createCharacter(Gender.MALE, Status.UNKNOWN))

        val result = useCase(characters)

        assertEquals(1, result[Status.UNKNOWN])
    }

    private fun createCharacter(gender: Gender, status: Status) = Character(
        id = 1,
        name = "Test Character",
        image = "test.jpg",
        gender = gender,
        status = status,
        species = "Human",
        origin = "Earth",
        location = "Earth",
        episodes = listOf(1)
    )

    private fun createTestCharacters() = listOf(
        createCharacter(1, "Rick Sanchez", Gender.MALE, Status.ALIVE),
        createCharacter(2, "Morty Smith", Gender.MALE, Status.ALIVE),
        createCharacter(3, "Summer Smith", Gender.FEMALE, Status.ALIVE),
        createCharacter(4, "Beth Smith", Gender.FEMALE, Status.ALIVE),
        createCharacter(5, "Jerry Smith", Gender.MALE, Status.DEAD),
        createCharacter(6, "Abadango Cluster Princess", Gender.FEMALE, Status.DEAD),
        createCharacter(7, "Gearhead", Gender.GENDERLESS, Status.UNKNOWN),
        createCharacter(8, "Agency Director", Gender.UNKNOWN, Status.ALIVE)
    )

    private fun createCharacter(id: Int, name: String, gender: Gender, status: Status) = Character(
        id = id,
        name = name,
        image = "test.jpg",
        gender = gender,
        status = status,
        species = "Human",
        origin = "Earth",
        location = "Earth",
        episodes = listOf(1)
    )
}
