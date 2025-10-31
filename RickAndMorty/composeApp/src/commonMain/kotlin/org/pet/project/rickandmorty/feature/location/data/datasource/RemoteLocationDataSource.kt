package org.pet.project.rickandmorty.feature.location.data.datasource

import org.pet.project.rickandmorty.core.result.Result
import org.pet.project.rickandmorty.feature.location.data.model.ApiLocationResponse
import org.pet.project.rickandmorty.feature.location.data.model.ResidentResponse

internal interface RemoteLocationDataSource {
    suspend fun getLocationByName(name: String): Result<ApiLocationResponse>

     suspend fun getResidents(id: Int): Result<ResidentResponse>
}