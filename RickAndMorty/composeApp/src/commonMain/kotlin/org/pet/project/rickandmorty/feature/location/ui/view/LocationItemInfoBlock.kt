package org.pet.project.rickandmorty.feature.location.ui.view

import androidx.compose.runtime.Composable
import org.pet.project.rickandmorty.feature.location.presentation.intent.LocationItemIntent
import org.pet.project.rickandmorty.feature.location.presentation.state.LocationItemInfoState

@Composable
internal fun LocationItemInfoBlock(
    state: LocationItemInfoState,
    onIntent: (LocationItemIntent) -> Unit
) {
    if (state.skeleton) {
        LocationItemInfoSkeleton()
    } else {
        checkNotNull(state.location)
        LocationItemInfoContent(
            name = state.location.name,
            type = state.location.type,
            dimension = state.location.dimension,
            amountResidents = state.location.amountResidents
        )
    }
}
