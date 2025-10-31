package org.pet.project.rickandmorty.feature.location.data.repository

import org.pet.project.rickandmorty.core.result.Result
import org.pet.project.rickandmorty.core.result.asSuccess
import org.pet.project.rickandmorty.core.result.asyncMap
import org.pet.project.rickandmorty.core.result.isFailure
import org.pet.project.rickandmorty.core.result.map
import org.pet.project.rickandmorty.feature.location.data.datasource.RemoteLocationDataSource
import org.pet.project.rickandmorty.feature.location.data.mapper.LocationMapper
import org.pet.project.rickandmorty.feature.location.data.model.LocationResponse
import org.pet.project.rickandmorty.feature.location.data.model.toItem
import org.pet.project.rickandmorty.feature.location.domain.entity.Location
import org.pet.project.rickandmorty.feature.location.domain.repository.LocationRepository

internal class LocationRepositoryImpl(
    private val remoteLocationDataSource: RemoteLocationDataSource,
    private val mapper: LocationMapper
) : LocationRepository {

    override suspend fun getLocationByName(name: String): Result<Location> {
        return remoteLocationDataSource.getLocationByName(name).map { response ->
            mapper.fromResponseToItem(response.results.first())
        }
    }
}
