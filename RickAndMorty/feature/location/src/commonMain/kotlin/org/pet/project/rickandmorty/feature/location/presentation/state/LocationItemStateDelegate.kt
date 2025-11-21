package org.pet.project.rickandmorty.feature.location.presentation.state

import org.pet.project.rickandmorty.feature.location.domain.entity.Location
import org.pet.project.rickandmorty.feature.location.domain.entity.Resident

internal fun LocationItemState.loading(): LocationItemState {
    return copy(
        locationState = locationState.copy(skeleton = true, error = false),
        residentState = residentState.copy(skeleton = true)
    )
}

internal fun LocationItemState.locationSuccess(location: Location): LocationItemState {
    return copy(locationState = locationState.copy(skeleton = false, location = location))
}

internal fun LocationItemState.locationFailure(): LocationItemState {
    return copy(locationState = locationState.copy(skeleton = false, error = true))
}

internal fun LocationItemState.uploadingResidents(): LocationItemState {
    return copy(residentState = residentState.copy(uploading = true, visibleMore = false))
}

internal fun LocationItemState.residentsSuccess(
    reached: Boolean,
    allResidents: List<Resident>,
    visibleResidents: List<Resident>,
): LocationItemState {
    return copy(
        residentState = residentState.copy(
            skeleton = false,
            uploading = false,
            visibleMore = !reached,
            residents = allResidents,
            visibleResidents = visibleResidents
        )
    )
}

internal fun LocationItemState.residentsFromCache(
    residents: List<Resident>
): LocationItemState {
    return copy(residentState = residentState.copy(visibleResidents = residents))
}

internal fun LocationItemState.residentsFailure(): LocationItemState {
    return copy(
        residentState = residentState.copy(
            skeleton = false,
            loadError = true,
            visibleMore = false
        )
    )
}

internal fun LocationItemState.residentsEmpty(): LocationItemState {
    return copy(
        residentState = residentState.copy(
            skeleton = false,
            loadError = true,
            visibleMore = false,
            uploadError = false
        )
    )
}

internal fun LocationItemState.residentsErrorUpload(): LocationItemState {
    return copy(
        residentState = residentState.copy(
            skeleton = false,
            uploading = false,
            uploadError = true,
            visibleMore = false,
            loadError = false
        )
    )
}