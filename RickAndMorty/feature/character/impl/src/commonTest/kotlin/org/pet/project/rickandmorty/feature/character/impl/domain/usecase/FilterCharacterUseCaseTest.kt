package org.pet.project.rickandmorty.feature.character.impl.domain.usecase

import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Character
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Filter
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Gender
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Status
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class FilterCharacterUseCaseTest {

    private lateinit var useCase: FilterCharacterUseCase
    private lateinit var testCharacters: List<Character>
    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setUp() {
        useCase = FilterCharacterUseCase(testDispatcher)
        testCharacters = createTestCharacters()
    }

    @Test
    fun `invoke with empty filters EXPECT all characters returned`() = runTest(testDispatcher) {
        val filters = emptyMap<String, List<Filter>>()

        val result = useCase(testCharacters, filters)

        assertEquals(testCharacters, result)
    }

    @Test
    fun `invoke with unselected filters EXPECT all characters returned`() = runTest(testDispatcher) {
        val filters = mapOf(
            "gender" to listOf(
                createFilter(Gender.MALE, selected = false),
                createFilter(Gender.FEMALE, selected = false)
            )
        )

        val result = useCase(testCharacters, filters)

        assertEquals(testCharacters, result)
    }

    @Test
    fun `invoke with single gender filter EXPECT matching characters returned`() = runTest(testDispatcher) {
        val filters = mapOf(
            "gender" to listOf(createFilter(Gender.MALE, selected = true))
        )

        val result = useCase(testCharacters, filters)

        assertEquals(3, result.size)
    }

    @Test
    fun `invoke with single status filter EXPECT matching characters returned`() = runTest(testDispatcher) {
        val filters = mapOf(
            "status" to listOf(createFilter(Status.ALIVE, selected = true))
        )

        val result = useCase(testCharacters, filters)

        assertEquals(5, result.size)
    }

    @Test
    fun `invoke with multiple gender filters EXPECT characters matching any gender returned`() = runTest(testDispatcher) {
        val filters = mapOf(
            "gender" to listOf(
                createFilter(Gender.MALE, selected = true),
                createFilter(Gender.FEMALE, selected = true)
            )
        )

        val result = useCase(testCharacters, filters)

        assertEquals(6, result.size)
    }

    @Test
    fun `invoke with multiple status filters EXPECT characters matching any status returned`() = runTest(testDispatcher) {
        val filters = mapOf(
            "status" to listOf(
                createFilter(Status.ALIVE, selected = true),
                createFilter(Status.DEAD, selected = true)
            )
        )

        val result = useCase(testCharacters, filters)

        assertEquals(7, result.size)
    }

    @Test
    fun `invoke with gender and status filters EXPECT characters matching both returned`() = runTest(testDispatcher) {
        val filters = mapOf(
            "gender" to listOf(createFilter(Gender.FEMALE, selected = true)),
            "status" to listOf(createFilter(Status.ALIVE, selected = true))
        )

        val result = useCase(testCharacters, filters)

        assertEquals(2, result.size)
    }

    @Test
    fun `invoke with empty character list EXPECT empty list returned`() = runTest(testDispatcher) {
        val filters = mapOf(
            "gender" to listOf(createFilter(Gender.MALE, selected = true))
        )

        val result = useCase(emptyList(), filters)

        assertEquals(emptyList(), result)
    }

    @Test
    fun `invoke with no matching filters EXPECT empty list returned`() = runTest(testDispatcher) {
        val filters = mapOf(
            "gender" to listOf(createFilter(Gender.GENDERLESS, selected = true)),
            "status" to listOf(createFilter(Status.DEAD, selected = true))
        )

        val result = useCase(testCharacters, filters)

        assertEquals(0, result.size)
    }

    @Test
    fun `invoke with mixed selected and unselected filters EXPECT only selected filters applied`() = runTest(testDispatcher) {
        val filters = mapOf(
            "gender" to listOf(
                createFilter(Gender.MALE, selected = true),
                createFilter(Gender.FEMALE, selected = false)
            ),
            "status" to listOf(
                createFilter(Status.ALIVE, selected = true),
                createFilter(Status.DEAD, selected = false)
            )
        )

        val result = useCase(testCharacters, filters)

        assertEquals(2, result.size)
    }

    @Test
    fun `invoke with genderless filter EXPECT genderless character returned`() = runTest(testDispatcher) {
        val filters = mapOf(
            "gender" to listOf(createFilter(Gender.GENDERLESS, selected = true))
        )

        val result = useCase(testCharacters, filters)

        assertEquals(1, result.size)
    }

    @Test
    fun `invoke with unknown gender filter EXPECT unknown gender character returned`() = runTest(testDispatcher) {
        val filters = mapOf(
            "gender" to listOf(createFilter(Gender.UNKNOWN, selected = true))
        )

        val result = useCase(testCharacters, filters)

        assertEquals(1, result.size)
    }

    @Test
    fun `invoke with unknown status filter EXPECT unknown status character returned`() = runTest(testDispatcher) {
        val filters = mapOf(
            "status" to listOf(createFilter(Status.UNKNOWN, selected = true))
        )

        val result = useCase(testCharacters, filters)

        assertEquals(1, result.size)
    }

    private fun createFilter(filter: Gender, selected: Boolean) = Filter(
        amount = 0,
        name = filter.name,
        selected = selected,
        filter = filter
    )

    private fun createFilter(filter: Status, selected: Boolean) = Filter(
        amount = 0,
        name = filter.name,
        selected = selected,
        filter = filter
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
