package org.pet.project.rickandmorty.feature.location.impl.data.paginator

import org.pet.project.rickandmorty.feature.location.impl.data.model.ResidentResponse
import org.pet.project.rickandmorty.library.result.Result

internal sealed interface RequestResidentState {
    object Loading : RequestResidentState
    object Error : RequestResidentState
    class Success(val value: List<Result<ResidentResponse>>, val reached: Boolean) : RequestResidentState
    object Ended : RequestResidentState
}