package org.pet.project.rickandmorty.feature.location.impl.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.runTest
import org.pet.project.rickandmorty.feature.location.api.domain.entity.Location
import org.pet.project.rickandmorty.feature.location.api.domain.entity.Resident
import org.pet.project.rickandmorty.feature.location.api.domain.entity.ResidentState
import org.pet.project.rickandmorty.feature.location.api.domain.repository.LocationRepository
import org.pet.project.rickandmorty.library.result.Result
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class LoadNextResidentsUseCaseTest {

    private fun createResident(
        id: Int = 1,
        name: String = "Resident $id",
        image: String = "https://example.com/image/$id.png"
    ): Resident {
        return Resident(id = id, name = name, image = image)
    }

    private fun createResidents(count: Int): List<Resident> {
        return (1..count).map { createResident(id = it) }
    }

    @Test
    fun `invoke with visible size less than all size EXPECT returns next chunk`() = runTest {
        val repository = FakeLocationRepository()
        val useCase = LoadNextResidentsUseCase(repository)
        val visible = createResidents(2)
        val all = createResidents(10)

        val result = useCase.invoke(visible, all)

        assertEquals(4, result?.size)
    }

    @Test
    fun `invoke with visible size less than all size EXPECT returns correct residents`() = runTest {
        val repository = FakeLocationRepository()
        val useCase = LoadNextResidentsUseCase(repository)
        val visible = createResidents(2)
        val all = createResidents(10)

        val result = useCase.invoke(visible, all)

        assertEquals(3, result?.first()?.id)
    }

    @Test
    fun `invoke with visible size less than all size EXPECT last resident id correct`() = runTest {
        val repository = FakeLocationRepository()
        val useCase = LoadNextResidentsUseCase(repository)
        val visible = createResidents(2)
        val all = createResidents(10)

        val result = useCase.invoke(visible, all)

        assertEquals(6, result?.last()?.id)
    }

    @Test
    fun `invoke with visible size equal to all size EXPECT returns null`() = runTest {
        val repository = FakeLocationRepository()
        val useCase = LoadNextResidentsUseCase(repository)
        val all = createResidents(5)
        val visible = all

        val result = useCase.invoke(visible, all)

        assertNull(result)
    }

    @Test
    fun `invoke with visible size equal to all size EXPECT loadNextResidents called`() = runTest {
        val repository = FakeLocationRepository()
        val useCase = LoadNextResidentsUseCase(repository)
        val all = createResidents(5)
        val visible = all

        useCase.invoke(visible, all)

        assertTrue(repository.loadNextResidentsCalled)
    }

    @Test
    fun `invoke with visible size greater than all size EXPECT returns null`() = runTest {
        val repository = FakeLocationRepository()
        val useCase = LoadNextResidentsUseCase(repository)
        val visible = createResidents(10)
        val all = createResidents(5)

        val result = useCase.invoke(visible, all)

        assertNull(result)
    }

    @Test
    fun `invoke with visible size greater than all size EXPECT loadNextResidents called`() = runTest {
        val repository = FakeLocationRepository()
        val useCase = LoadNextResidentsUseCase(repository)
        val visible = createResidents(10)
        val all = createResidents(5)

        useCase.invoke(visible, all)

        assertTrue(repository.loadNextResidentsCalled)
    }

    @Test
    fun `invoke with remaining items less than chunk size EXPECT returns all remaining`() = runTest {
        val repository = FakeLocationRepository()
        val useCase = LoadNextResidentsUseCase(repository)
        val visible = createResidents(8)
        val all = createResidents(10)

        val result = useCase.invoke(visible, all)

        assertEquals(2, result?.size)
    }

    @Test
    fun `invoke with empty visible list EXPECT returns first chunk`() = runTest {
        val repository = FakeLocationRepository()
        val useCase = LoadNextResidentsUseCase(repository)
        val visible = emptyList<Resident>()
        val all = createResidents(10)

        val result = useCase.invoke(visible, all)

        assertEquals(4, result?.size)
    }

    @Test
    fun `invoke with empty visible list EXPECT first resident id is 1`() = runTest {
        val repository = FakeLocationRepository()
        val useCase = LoadNextResidentsUseCase(repository)
        val visible = emptyList<Resident>()
        val all = createResidents(10)

        val result = useCase.invoke(visible, all)

        assertEquals(1, result?.first()?.id)
    }

    @Test
    fun `invoke with one visible element EXPECT returns next chunk starting from index 1`() = runTest {
        val repository = FakeLocationRepository()
        val useCase = LoadNextResidentsUseCase(repository)
        val visible = createResidents(1)
        val all = createResidents(10)

        val result = useCase.invoke(visible, all)

        assertEquals(2, result?.first()?.id)
    }

    @Test
    fun `invoke with exactly chunk size remaining EXPECT returns full chunk`() = runTest {
        val repository = FakeLocationRepository()
        val useCase = LoadNextResidentsUseCase(repository)
        val visible = createResidents(6)
        val all = createResidents(10)

        val result = useCase.invoke(visible, all)

        assertEquals(4, result?.size)
    }

    @Test
    fun `invoke with both empty lists EXPECT loadNextResidents called`() = runTest {
        val repository = FakeLocationRepository()
        val useCase = LoadNextResidentsUseCase(repository)
        val visible = emptyList<Resident>()
        val all = emptyList<Resident>()

        useCase.invoke(visible, all)

        assertTrue(repository.loadNextResidentsCalled)
    }

    @Test
    fun `invoke with both empty lists EXPECT returns null`() = runTest {
        val repository = FakeLocationRepository()
        val useCase = LoadNextResidentsUseCase(repository)
        val visible = emptyList<Resident>()
        val all = emptyList<Resident>()

        val result = useCase.invoke(visible, all)

        assertNull(result)
    }

    @Test
    fun `CHUNK_SIZE constant EXPECT equals 4`() {
        assertEquals(4, LoadNextResidentsUseCase.CHUNK_SIZE)
    }

    private class FakeLocationRepository : LocationRepository {
        override val residents: Flow<ResidentState> = emptyFlow()

        var loadNextResidentsCalled = false
            private set

        override suspend fun getLocationByName(name: String): Result<Location> {
            throw NotImplementedError("Not used in this test")
        }

        override suspend fun loadNextResidents() {
            loadNextResidentsCalled = true
        }
    }
}
