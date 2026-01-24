package org.pet.project.rickandmorty.feature.character.impl.data.paginator

import kotlinx.coroutines.test.runTest
import org.pet.project.rickandmorty.common.data.InfoResponse
import org.pet.project.rickandmorty.common.data.PaginatorState
import org.pet.project.rickandmorty.feature.character.impl.data.datasource.RemoteCharacterDataSource
import org.pet.project.rickandmorty.feature.character.impl.data.model.CharacterListResponse
import org.pet.project.rickandmorty.feature.character.impl.data.model.CharacterResponse
import org.pet.project.rickandmorty.feature.character.impl.data.model.LocationResponse
import org.pet.project.rickandmorty.feature.character.impl.data.model.OriginResponse
import org.pet.project.rickandmorty.library.result.Result
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class CharacterPaginatorTest {

    private lateinit var fakeDataSource: FakeRemoteCharacterDataSource
    private lateinit var paginator: CharacterPaginator

    @BeforeTest
    fun setUp() {
        fakeDataSource = FakeRemoteCharacterDataSource()
        paginator = CharacterPaginator(fakeDataSource)
    }

    private fun createCharacterResponse(id: Int, name: String): CharacterResponse {
        return CharacterResponse(
            id = id,
            name = name,
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            origin = OriginResponse("Earth", ""),
            location = LocationResponse("Earth", ""),
            image = "https://example.com/$id.jpeg",
            episode = listOf("https://rickandmortyapi.com/api/episode/1"),
            url = "https://rickandmortyapi.com/api/character/$id",
            created = "2017-11-04T18:48:46.250Z"
        )
    }

    private fun createPage(
        page: Int,
        totalPages: Int,
        characters: List<CharacterResponse>,
        hasNext: Boolean = true
    ): CharacterListResponse {
        val nextUrl = if (hasNext && page < totalPages) {
            "https://rickandmortyapi.com/api/character?page=${page + 1}"
        } else null
        val prevUrl = if (page > 1) {
            "https://rickandmortyapi.com/api/character?page=${page - 1}"
        } else null

        return CharacterListResponse(
            info = InfoResponse(
                count = characters.size * totalPages,
                pages = totalPages,
                next = nextUrl,
                prev = prevUrl
            ),
            results = characters
        )
    }

    @Test
    fun `loadItems first time EXPECT delegate to remoteDataSource getCharactersPage`() = runTest {
        val character = createCharacterResponse(1, "Rick Sanchez")
        val page1 = createPage(1, 42, listOf(character))
        fakeDataSource.setPageResponse(1, Result.Success.Value(page1))

        paginator.loadItems()

        assertEquals(1, fakeDataSource.getCharactersPageCallCount)
    }

    @Test
    fun `loadItems first time EXPECT call page 1`() = runTest {
        val character = createCharacterResponse(1, "Rick Sanchez")
        val page1 = createPage(1, 42, listOf(character))
        fakeDataSource.setPageResponse(1, Result.Success.Value(page1))

        paginator.loadItems()

        assertEquals(1, fakeDataSource.lastRequestedPage)
    }

    @Test
    fun `getNextKey with available next page EXPECT return next page from info`() = runTest {
        val character = createCharacterResponse(1, "Rick Sanchez")
        val page1 = createPage(1, 42, listOf(character))
        fakeDataSource.setPageResponse(1, Result.Success.Value(page1))

        paginator.loadItems()
        val state = paginator.paginationFlow.value
        assertIs<PaginatorState.Success<CharacterListResponse>>(state)

        assertEquals(2, state.value.info.getNextPage())
    }

    @Test
    fun `getNextKey with null next page EXPECT return currentKey plus one`() = runTest {
        val character = createCharacterResponse(1, "Rick Sanchez")
        // Page with null next URL but many pages remaining
        val pageWithNullNext = CharacterListResponse(
            info = InfoResponse(count = 100, pages = 50, next = null, prev = null),
            results = listOf(character)
        )
        val page2 = createPage(2, 50, listOf(character))
        fakeDataSource.setPageResponse(1, Result.Success.Value(pageWithNullNext))
        fakeDataSource.setPageResponse(2, Result.Success.Value(page2))

        paginator.loadItems() // loads page 1, next key becomes 1+1=2
        paginator.loadItems() // loads page 2

        assertEquals(2, fakeDataSource.lastRequestedPage)
    }

    @Test
    fun `checkEndReached when currentKey greater than allPages EXPECT End state`() = runTest {
        val character = createCharacterResponse(1, "Rick Sanchez")
        val smallPage = createPage(1, 2, listOf(character), hasNext = false)
        fakeDataSource.setPageResponse(1, Result.Success.Value(smallPage))
        fakeDataSource.setDefaultResponse(Result.Success.Value(smallPage))

        repeat(5) { paginator.loadItems() }
        val state = paginator.paginationFlow.value

        assertIs<PaginatorState.End>(state)
    }

    @Test
    fun `checkEndReached when currentKey less than allPages EXPECT Success state`() = runTest {
        val character = createCharacterResponse(1, "Rick Sanchez")
        val page1 = createPage(1, 42, listOf(character))
        fakeDataSource.setPageResponse(1, Result.Success.Value(page1))

        paginator.loadItems()
        val state = paginator.paginationFlow.value

        assertIs<PaginatorState.Success<CharacterListResponse>>(state)
    }

    @Test
    fun `loadItems initial state EXPECT Initial`() = runTest {
        val state = paginator.paginationFlow.value

        assertIs<PaginatorState.Initial>(state)
    }

    @Test
    fun `loadItems with success EXPECT Success state`() = runTest {
        val character = createCharacterResponse(1, "Rick Sanchez")
        val page1 = createPage(1, 42, listOf(character))
        fakeDataSource.setPageResponse(1, Result.Success.Value(page1))

        paginator.loadItems()
        val state = paginator.paginationFlow.value

        assertIs<PaginatorState.Success<CharacterListResponse>>(state)
    }

    @Test
    fun `loadItems with error EXPECT Error state`() = runTest {
        val exception = RuntimeException("Network error")
        fakeDataSource.setPageResponse(1, Result.Failure.Error(exception))

        paginator.loadItems()
        val state = paginator.paginationFlow.value

        assertIs<PaginatorState.Error>(state)
    }

    @Test
    fun `loadItems with error EXPECT correct exception`() = runTest {
        val exception = RuntimeException("Network error")
        fakeDataSource.setPageResponse(1, Result.Failure.Error(exception))

        paginator.loadItems()
        val state = paginator.paginationFlow.value
        assertIs<PaginatorState.Error>(state)

        assertEquals(exception, state.throwable)
    }

    @Test
    fun `loadItems when end reached EXPECT no additional remoteDataSource call`() = runTest {
        val character = createCharacterResponse(1, "Rick Sanchez")
        val singlePage = createPage(1, 1, listOf(character), hasNext = false)
        fakeDataSource.setPageResponse(1, Result.Success.Value(singlePage))

        paginator.loadItems()
        val callCountAfterFirst = fakeDataSource.getCharactersPageCallCount

        paginator.loadItems()
        val callCountAfterSecond = fakeDataSource.getCharactersPageCallCount

        assertEquals(callCountAfterFirst, callCountAfterSecond)
    }

    @Test
    fun `loadItems multiple times EXPECT correct page sequence`() = runTest {
        val char1 = createCharacterResponse(1, "Rick Sanchez")
        val char2 = createCharacterResponse(2, "Morty Smith")
        val page1 = createPage(1, 42, listOf(char1))
        val page2 = createPage(2, 42, listOf(char2))
        fakeDataSource.setPageResponse(1, Result.Success.Value(page1))
        fakeDataSource.setPageResponse(2, Result.Success.Value(page2))

        paginator.loadItems()
        paginator.loadItems()

        assertEquals(2, fakeDataSource.lastRequestedPage)
    }

    @Test
    fun `loadItems multiple times EXPECT correct character on second page`() = runTest {
        val char1 = createCharacterResponse(1, "Rick Sanchez")
        val char2 = createCharacterResponse(2, "Morty Smith")
        val page1 = createPage(1, 42, listOf(char1))
        val page2 = createPage(2, 42, listOf(char2))
        fakeDataSource.setPageResponse(1, Result.Success.Value(page1))
        fakeDataSource.setPageResponse(2, Result.Success.Value(page2))

        paginator.loadItems()
        paginator.loadItems()
        val state = paginator.paginationFlow.value
        assertIs<PaginatorState.Success<CharacterListResponse>>(state)

        assertEquals("Morty Smith", state.value.results.first().name)
    }

    @Test
    fun `loadItems reaching last page EXPECT End state`() = runTest {
        val character = createCharacterResponse(1, "Rick Sanchez")
        // Single page response: after loading, currentKey becomes 2, and 2 > 1 = true (end reached)
        val singlePage = createPage(1, 1, listOf(character), hasNext = false)
        fakeDataSource.setPageResponse(1, Result.Success.Value(singlePage))

        paginator.loadItems()
        val state = paginator.paginationFlow.value

        assertIs<PaginatorState.End>(state)
    }

    @Test
    fun `loadItems with success EXPECT correct count value`() = runTest {
        val character = createCharacterResponse(1, "Rick Sanchez")
        val page1 = createPage(1, 42, listOf(character))
        fakeDataSource.setPageResponse(1, Result.Success.Value(page1))

        paginator.loadItems()
        val state = paginator.paginationFlow.value
        assertIs<PaginatorState.Success<CharacterListResponse>>(state)

        assertEquals(42, state.value.info.count)
    }

    @Test
    fun `loadItems after error EXPECT can retry successfully`() = runTest {
        val character = createCharacterResponse(1, "Rick Sanchez")
        val page1 = createPage(1, 42, listOf(character))
        val exception = RuntimeException("Network error")

        fakeDataSource.setPageResponse(1, Result.Failure.Error(exception))
        paginator.loadItems()
        assertIs<PaginatorState.Error>(paginator.paginationFlow.value)

        fakeDataSource.setPageResponse(1, Result.Success.Value(page1))
        paginator.loadItems()
        val state = paginator.paginationFlow.value

        assertIs<PaginatorState.Success<CharacterListResponse>>(state)
    }

    @Test
    fun `loadItems with null next URL EXPECT calculate next key as currentKey plus one`() = runTest {
        val char1 = createCharacterResponse(1, "Rick Sanchez")
        val char2 = createCharacterResponse(2, "Morty Smith")
        val pageWithNullNext = CharacterListResponse(
            info = InfoResponse(count = 100, pages = 50, next = null, prev = null),
            results = listOf(char1)
        )
        val page2 = createPage(2, 50, listOf(char2))
        fakeDataSource.setPageResponse(1, Result.Success.Value(pageWithNullNext))
        fakeDataSource.setPageResponse(2, Result.Success.Value(page2))

        paginator.loadItems()
        paginator.loadItems()

        assertEquals(2, fakeDataSource.lastRequestedPage)
    }

    @Test
    fun `success response EXPECT correct pages count`() = runTest {
        val character = createCharacterResponse(1, "Rick Sanchez")
        val page1 = createPage(1, 42, listOf(character))
        fakeDataSource.setPageResponse(1, Result.Success.Value(page1))

        paginator.loadItems()
        val state = paginator.paginationFlow.value
        assertIs<PaginatorState.Success<CharacterListResponse>>(state)

        assertEquals(42, state.value.info.pages)
    }

    @Test
    fun `success response EXPECT correct character name`() = runTest {
        val character = createCharacterResponse(1, "Rick Sanchez")
        val page1 = createPage(1, 42, listOf(character))
        fakeDataSource.setPageResponse(1, Result.Success.Value(page1))

        paginator.loadItems()
        val state = paginator.paginationFlow.value
        assertIs<PaginatorState.Success<CharacterListResponse>>(state)

        assertEquals("Rick Sanchez", state.value.results.first().name)
    }

    @Test
    fun `success response EXPECT correct results size`() = runTest {
        val character = createCharacterResponse(1, "Rick Sanchez")
        val page1 = createPage(1, 42, listOf(character))
        fakeDataSource.setPageResponse(1, Result.Success.Value(page1))

        paginator.loadItems()
        val state = paginator.paginationFlow.value
        assertIs<PaginatorState.Success<CharacterListResponse>>(state)

        assertEquals(1, state.value.results.size)
    }

    @Test
    fun `error response EXPECT correct exception message`() = runTest {
        val exception = IllegalStateException("Test exception")
        fakeDataSource.setPageResponse(1, Result.Failure.Error(exception))

        paginator.loadItems()
        val state = paginator.paginationFlow.value
        assertIs<PaginatorState.Error>(state)

        assertEquals("Test exception", state.throwable.message)
    }
}

private class FakeRemoteCharacterDataSource : RemoteCharacterDataSource {

    private val pageResponses = mutableMapOf<Int, Result<CharacterListResponse>>()
    private var defaultResponse: Result<CharacterListResponse>? = null

    var getCharactersPageCallCount = 0
        private set

    var lastRequestedPage: Int? = null
        private set

    fun setPageResponse(page: Int, response: Result<CharacterListResponse>) {
        pageResponses[page] = response
    }

    fun setDefaultResponse(response: Result<CharacterListResponse>) {
        defaultResponse = response
    }

    override suspend fun getCharactersPage(
        pageNumber: Int,
        params: Map<String, String>
    ): Result<CharacterListResponse> {
        getCharactersPageCallCount++
        lastRequestedPage = pageNumber
        return pageResponses[pageNumber]
            ?: defaultResponse
            ?: Result.Failure.Error(IllegalStateException("No response configured for page $pageNumber"))
    }

    override suspend fun getCharacter(id: Int): Result<CharacterResponse> {
        throw NotImplementedError("Not needed for CharacterPaginator tests")
    }

    override suspend fun getAllCharactersByName(name: String): Result<List<CharacterResponse>> {
        throw NotImplementedError("Not needed for CharacterPaginator tests")
    }
}
