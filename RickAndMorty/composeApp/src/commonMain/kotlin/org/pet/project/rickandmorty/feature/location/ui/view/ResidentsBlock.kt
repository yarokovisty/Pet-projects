package org.pet.project.rickandmorty.feature.location.ui.view

import androidx.compose.runtime.Composable
import org.pet.project.rickandmorty.feature.location.presentation.intent.LocationItemIntent
import org.pet.project.rickandmorty.feature.location.presentation.state.ResidentListState

@Composable
internal fun ResidentsBlock(
    state: ResidentListState,
    onIntent: (LocationItemIntent) -> Unit
) {
    if (state.skeleton) {
        ResidentsSkeleton()
    } else {
        ResidentsContent(state)
    }
}
