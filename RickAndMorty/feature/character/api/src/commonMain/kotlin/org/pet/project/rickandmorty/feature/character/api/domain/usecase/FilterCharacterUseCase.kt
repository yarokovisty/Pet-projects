package org.pet.project.rickandmorty.feature.character.api.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Character
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Filter
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Gender
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Status

class FilterCharacterUseCase(private val dispatcher: CoroutineDispatcher) {

	suspend operator fun invoke(
		characters: List<Character>,
		filters: Map<String, List<Filter>>
	): List<Character> = withContext(dispatcher) {
		characters.filter { character ->
			val selectedFilters = filters.values.flatten().filter { it.selected }

			if (selectedFilters.isEmpty()) return@filter true

			val selectedGenders = selectedFilters.filter { it.filter is Gender }
			val selectedStatuses = selectedFilters.filter { it.filter is Status }

			val matchesGender = selectedGenders.isEmpty() || selectedGenders.any { it.filter == character.gender }

			val matchesStatus = selectedStatuses.isEmpty() || selectedStatuses.any { it.filter == character.status }

			matchesGender && matchesStatus
		}
	}
}