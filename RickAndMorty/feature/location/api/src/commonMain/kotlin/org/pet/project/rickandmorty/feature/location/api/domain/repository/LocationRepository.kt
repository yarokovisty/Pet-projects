package org.pet.project.rickandmorty.feature.location.api.domain.repository

import kotlinx.coroutines.flow.Flow
import org.pet.project.rickandmorty.feature.location.api.domain.entity.Location
import org.pet.project.rickandmorty.feature.location.api.domain.entity.ResidentState
import org.pet.project.rickandmorty.library.result.Result

interface LocationRepository {
    val residents: Flow<ResidentState>

    suspend fun getLocationByName(name: String): Result<Location>

    suspend fun loadNextResidents()
}