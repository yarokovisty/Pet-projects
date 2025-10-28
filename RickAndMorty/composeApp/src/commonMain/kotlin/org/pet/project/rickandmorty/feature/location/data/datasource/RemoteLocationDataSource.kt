package org.pet.project.rickandmorty.feature.location.data.datasource

import org.pet.project.rickandmorty.core.result.Result
import org.pet.project.rickandmorty.feature.location.data.model.LocationResponse

 internal interface RemoteLocationDataSource {
    suspend fun getLocationByName(name: String): Result<LocationResponse>
}