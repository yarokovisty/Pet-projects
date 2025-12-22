package org.pet.project.rickandmorty.feature.location.api.domain.entity

sealed interface ResidentState {
    object Loading : ResidentState
    object Error : ResidentState
    class Success(val value: List<Resident>, val reached: Boolean) : ResidentState
    object Ended : ResidentState
}