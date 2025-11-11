package org.pet.project.rickandmorty.feature.location.data.paginator

import org.pet.project.rickandmorty.core.result.Result
import org.pet.project.rickandmorty.feature.location.data.model.ResidentResponse

internal sealed interface RequestResidentState {
    object Loading : RequestResidentState
    object Error : RequestResidentState
    class Success(val value: List<Result<ResidentResponse>>, val reached: Boolean) : RequestResidentState
    object Ended : RequestResidentState
}