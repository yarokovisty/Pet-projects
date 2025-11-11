package org.pet.project.rickandmorty.feature.location.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import org.pet.project.rickandmorty.design.component.AppErrorScreen
import org.pet.project.rickandmorty.design.component.AppFullScreen
import org.pet.project.rickandmorty.design.component.AppSpacer
import org.pet.project.rickandmorty.design.component.AppToolbarNavBackIcon
import org.pet.project.rickandmorty.feature.location.navigation.LocationNavigator
import org.pet.project.rickandmorty.feature.location.presentation.event.LocationItemEvent
import org.pet.project.rickandmorty.feature.location.presentation.intent.LocationItemIntent
import org.pet.project.rickandmorty.feature.location.presentation.state.LocationItemState
import org.pet.project.rickandmorty.feature.location.presentation.viewmodel.LocationItemViewModel
import org.pet.project.rickandmorty.feature.location.ui.view.LocationItemInfoBlock
import org.pet.project.rickandmorty.feature.location.ui.view.ResidentsBlock
import org.pet.project.rickandmorty.utils.collectAsEffect

private typealias LocationName = String

@Composable
fun LocationItemScreen(
    name: LocationName,
    navigator: LocationNavigator
) {
    val viewModel = koinViewModel<LocationItemViewModel> { parametersOf(name) }
    val state by viewModel.state.collectAsState()

    LocationItemScreen(
        navigator = navigator,
        state = state,
        event = viewModel.event,
        onIntent = viewModel::onIntent
    )
}

@Composable
private fun LocationItemScreen(
    navigator: LocationNavigator,
    state: LocationItemState,
    event: Flow<LocationItemEvent>,
    onIntent: (LocationItemIntent) -> Unit
) {
    AppFullScreen {
        Column(modifier = Modifier.fillMaxSize()) {
            Toolbar(onBack = { onIntent(LocationItemIntent.NavigateBack) })

            if (state.locationState.error) {
                AppErrorScreen(
                    onClick = { onIntent(LocationItemIntent.Refresh) }
                )
            } else {
                LocationItemInfoBlock(
                    state = state.locationState,
                    onIntent = onIntent
                )

                AppSpacer(height = 20.dp)

                ResidentsBlock(
                    state = state.residentState,
                    onIntent = onIntent
                )
            }

        }
    }

    event.collectAsEffect { e ->
        when(e) {
            LocationItemEvent.NavigateBack -> navigator.back()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Toolbar(onBack: () -> Unit) {
    TopAppBar(
        title = {},
        navigationIcon = { AppToolbarNavBackIcon(onClick = onBack) }
    )
}
