package org.pet.project.rickandmorty.feature.location.presentation.event

import org.pet.project.rickandmorty.common.presentation.Event

sealed interface LocationItemEvent : Event {
    object NavigateBack : LocationItemEvent
    object ErrorUploadResidents : LocationItemEvent
}