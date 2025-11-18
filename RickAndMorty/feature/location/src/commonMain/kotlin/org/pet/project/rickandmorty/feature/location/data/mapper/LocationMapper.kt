package org.pet.project.rickandmorty.feature.location.data.mapper

import org.pet.project.rickandmorty.feature.location.data.model.LocationResponse
import org.pet.project.rickandmorty.feature.location.data.model.ResidentResponse
import org.pet.project.rickandmorty.feature.location.data.paginator.RequestResidentState
import org.pet.project.rickandmorty.feature.location.domain.entity.Location
import org.pet.project.rickandmorty.feature.location.domain.entity.Resident
import org.pet.project.rickandmorty.feature.location.domain.entity.ResidentState
import org.pet.project.rickandmorty.library.result.asSuccess
import org.pet.project.rickandmorty.library.result.isSuccess

internal fun LocationResponse.toItem(amountResidents: Int) =
    Location(
        id = this.id,
        name = this.name,
        type = this.type,
        dimension = this.dimension,
        amountResidents = amountResidents
    )

internal fun ResidentResponse.toItem(): Resident {
    return Resident(
        id = this.id,
        name = this.name,
        image = this.image
    )
}

internal fun RequestResidentState.toItem(): ResidentState {
    return when(this) {
        is RequestResidentState.Loading -> ResidentState.Loading
        is RequestResidentState.Error -> ResidentState.Error
        is RequestResidentState.Ended -> ResidentState.Ended
        is RequestResidentState.Success -> {
            val residents = this.value.asSequence()
                .filter { response -> response.isSuccess() }
                .map { response -> response.asSuccess().value.toItem() }
                .toList()
            ResidentState.Success(residents, this.reached)
        }
    }
}
