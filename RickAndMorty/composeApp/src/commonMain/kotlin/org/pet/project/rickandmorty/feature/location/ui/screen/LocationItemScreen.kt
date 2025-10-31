package org.pet.project.rickandmorty.feature.location.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import org.pet.project.rickandmorty.design.component.AppErrorScreen
import org.pet.project.rickandmorty.design.component.AppFullScreen
import org.pet.project.rickandmorty.design.component.AppLoadingScreen
import org.pet.project.rickandmorty.feature.location.presentation.intent.LocationItemIntent
import org.pet.project.rickandmorty.feature.location.presentation.state.LocationItemScreenState
import org.pet.project.rickandmorty.feature.location.presentation.viewmodel.LocationItemViewModel
import org.pet.project.rickandmorty.feature.location.ui.view.LocationItemContentScreen
import org.pet.project.rickandmorty.utils.isNotNull

@Composable
fun LocationItemScreen(
    name: LocationName
) {
    val viewModel = koinViewModel<LocationItemViewModel> { parametersOf(name) }
    val state by viewModel.state.collectAsState()

    LocationItemScreen(
        state = state,
        onIntent = viewModel::onIntent
    )
}

@Composable
private fun LocationItemScreen(
    state: LocationItemScreenState,
    onIntent: (LocationItemIntent) -> Unit
) {
    AppFullScreen {
        when {
            state.location.isNotNull() -> LocationItemContentScreen(
                location = state.location,
                onIntent = {  }
            )

            state.loading -> AppLoadingScreen()

            state.error -> AppErrorScreen(
                onClick = { onIntent(LocationItemIntent.Refresh) }
            )
        }
    }
}

private typealias LocationName = String
