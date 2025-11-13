package org.pet.project.rickandmorty.feature.location.data.repository

import kotlinx.coroutines.flow.map
import org.pet.project.rickandmorty.feature.location.data.datasource.RemoteLocationDataSource
import org.pet.project.rickandmorty.feature.location.data.mapper.toItem
import org.pet.project.rickandmorty.feature.location.data.paginator.RequestResidentState
import org.pet.project.rickandmorty.feature.location.data.paginator.ResidentsPaginator
import org.pet.project.rickandmorty.feature.location.domain.entity.Location
import org.pet.project.rickandmorty.feature.location.domain.repository.LocationRepository
import org.pet.project.rickandmorty.library.result.Result
import org.pet.project.rickandmorty.library.result.isSuccess
import org.pet.project.rickandmorty.library.result.map

internal class LocationRepositoryImpl(
	private val remoteLocationDataSource: RemoteLocationDataSource,
	private val residentsPaginator: ResidentsPaginator
) : LocationRepository {

	override val residents = residentsPaginator.residentsFlow
		.map(RequestResidentState::toItem)

	override suspend fun getLocationByName(name: String): Result<Location> {
		return remoteLocationDataSource.getLocationByName(name)
			.also { response ->
				if (response.isSuccess()) {
					val urls = response.value.results.first().residents
					residentsPaginator.setResidentsUrls(urls)
				}
			}
			.map { response ->
				val amountResidents = response.results.first().residents.size
				response.results.first().toItem(amountResidents)
			}
	}

	override suspend fun loadNextResidents() {
		residentsPaginator.loadNextResidents()
	}
}

