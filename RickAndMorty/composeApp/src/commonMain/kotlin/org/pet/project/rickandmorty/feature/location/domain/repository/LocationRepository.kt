package org.pet.project.rickandmorty.feature.location.domain.repository

import org.pet.project.rickandmorty.core.result.Result
import org.pet.project.rickandmorty.feature.location.domain.entity.Location

interface LocationRepository {
    suspend fun getLocationByName(name: String): Result<Location>

}