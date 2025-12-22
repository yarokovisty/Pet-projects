package org.pet.project.rickandmorty.feature.location.api.domain.usecase

import org.pet.project.rickandmorty.feature.location.api.domain.entity.Resident
import org.pet.project.rickandmorty.feature.location.api.domain.repository.LocationRepository

class LoadNextResidentsUseCase(private val repository: LocationRepository) {

	companion object {

		const val CHUNK_SIZE = 4
	}

	suspend operator fun invoke(
		visible: List<Resident>,
		all: List<Resident>
	): List<Resident>? {
		val allLastIndex = all.size
		val visibleLastIndex = visible.size

		if ((visibleLastIndex) >= allLastIndex) {
			repository.loadNextResidents()
			return null
		}

		val start = visible.size
		val end = minOf(start + CHUNK_SIZE, all.size)
		val nextChunk = all.subList(start, end)

		return nextChunk
	}
}