package org.pet.project.rickandmorty.feature.location.impl.data.datasource

import org.pet.project.rickandmorty.feature.location.impl.data.model.ApiLocationResponse
import org.pet.project.rickandmorty.feature.location.impl.data.model.ResidentResponse
import org.pet.project.rickandmorty.library.result.Result

internal interface RemoteLocationDataSource {
    suspend fun getLocationByName(name: String): Result<ApiLocationResponse>

     suspend fun getResidents(id: Int): Result<ResidentResponse>
}