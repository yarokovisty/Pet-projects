package org.pet.project.rickandmorty.feature.location.domain.repository

import kotlinx.coroutines.flow.Flow
import org.pet.project.rickandmorty.library.result.Result
import org.pet.project.rickandmorty.feature.location.domain.entity.Location
import org.pet.project.rickandmorty.feature.location.domain.entity.ResidentState

interface LocationRepository {
    val residents: Flow<ResidentState>

    suspend fun getLocationByName(name: String): Result<Location>

    suspend fun loadNextResidents()
}