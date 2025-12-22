package org.pet.project.rickandmorty.feature.location.impl.presentation.intent

import org.pet.project.rickandmorty.common.presentation.Intent

sealed interface LocationItemIntent : Intent {
    object Refresh : LocationItemIntent
    object NavigateBack : LocationItemIntent
    object UploadResidents : LocationItemIntent
}