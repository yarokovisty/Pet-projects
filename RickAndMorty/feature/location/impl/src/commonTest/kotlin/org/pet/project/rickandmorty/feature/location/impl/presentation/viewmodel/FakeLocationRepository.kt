package org.pet.project.rickandmorty.feature.location.impl.presentation.viewmodel

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import org.pet.project.rickandmorty.feature.location.api.domain.entity.Location
import org.pet.project.rickandmorty.feature.location.api.domain.entity.Resident
import org.pet.project.rickandmorty.feature.location.api.domain.entity.ResidentState
import org.pet.project.rickandmorty.feature.location.api.domain.repository.LocationRepository
import org.pet.project.rickandmorty.library.result.Result

internal class FakeLocationRepository : LocationRepository {

    private val _residents = MutableSharedFlow<ResidentState>(replay = 1)
    override val residents: Flow<ResidentState> = _residents

    var loadNextResidentsCallCount = 0
    var getLocationByNameCallCount = 0

    var getLocationByNameResult: Result<Location> = Result.Success.Value(createTestLocation())

    suspend fun emitResidentState(state: ResidentState) {
        _residents.emit(state)
    }

    override suspend fun getLocationByName(name: String): Result<Location> {
        getLocationByNameCallCount++
        return getLocationByNameResult
    }

    override suspend fun loadNextResidents() {
        loadNextResidentsCallCount++
    }

    companion object {
        fun createTestLocation(
            id: Int = 1,
            name: String = "Earth (C-137)",
            type: String = "Planet",
            dimension: String = "Dimension C-137",
            amountResidents: Int = 10
        ) = Location(
            id = id,
            name = name,
            type = type,
            dimension = dimension,
            amountResidents = amountResidents
        )

        fun createTestResident(
            id: Int = 1,
            name: String = "Rick Sanchez",
            image: String = "https://rickandmortyapi.com/api/character/avatar/$id.jpeg"
        ) = Resident(
            id = id,
            name = name,
            image = image
        )

        fun createTestResidentList(count: Int = 3): List<Resident> =
            (1..count).map { createTestResident(id = it, name = "Resident $it") }
    }
}
