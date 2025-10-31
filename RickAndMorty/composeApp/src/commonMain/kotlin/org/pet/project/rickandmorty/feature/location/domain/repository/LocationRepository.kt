package org.pet.project.rickandmorty.feature.location.domain.repository

import kotlinx.coroutines.flow.Flow
import org.pet.project.rickandmorty.core.result.Result
import org.pet.project.rickandmorty.feature.location.domain.entity.Location
import org.pet.project.rickandmorty.feature.location.domain.entity.Resident

interface LocationRepository {
    val residents: Flow<List<Resident>>

    suspend fun getLocationByName(name: String): Result<Location>

    suspend fun loadNextResidents()
}