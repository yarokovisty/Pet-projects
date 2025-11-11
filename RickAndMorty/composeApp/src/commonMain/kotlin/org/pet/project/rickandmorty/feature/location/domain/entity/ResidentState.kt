package org.pet.project.rickandmorty.feature.location.domain.entity

sealed interface ResidentState {
    object Loading : ResidentState
    object Error : ResidentState
    class Success(val value: List<Resident>, val reached: Boolean) : ResidentState
    object Ended : ResidentState
}