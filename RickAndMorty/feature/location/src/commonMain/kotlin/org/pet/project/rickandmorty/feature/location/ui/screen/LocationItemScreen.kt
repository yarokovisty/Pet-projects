package org.pet.project.rickandmorty.feature.location.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import org.jetbrains.compose.resources.getString
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import org.pet.project.rickandmorty.design.component.AppErrorScreen
import org.pet.project.rickandmorty.design.component.AppSpacer
import org.pet.project.rickandmorty.design.component.AppToolbarNavBackIcon
import org.pet.project.rickandmorty.feature.location.navigation.LocalLocationNavigator
import org.pet.project.rickandmorty.feature.location.navigation.LocationNavigator
import org.pet.project.rickandmorty.feature.location.presentation.event.LocationItemEvent
import org.pet.project.rickandmorty.feature.location.presentation.intent.LocationItemIntent
import org.pet.project.rickandmorty.feature.location.presentation.state.LocationItemState
import org.pet.project.rickandmorty.feature.location.presentation.viewmodel.LocationItemViewModel
import org.pet.project.rickandmorty.feature.location.ui.view.LocationItemInfoContent
import org.pet.project.rickandmorty.feature.location.ui.view.LocationItemInfoSkeleton
import org.pet.project.rickandmorty.feature.location.ui.view.ResidentListContent
import org.pet.project.rickandmorty.feature.location.ui.view.ResidentListSkeleton
import org.pet.project.rickandmorty.util.collectAsEffect
import rickandmorty.feature.location.generated.resources.Res
import rickandmorty.feature.location.generated.resources.location_error_upload_residents

private typealias LocationName = String

@Composable
internal fun LocationItemScreen(name: LocationName) {
	val navigator = LocalLocationNavigator.current
	val viewModel = koinViewModel<LocationItemViewModel> { parametersOf(name) }
	val state by viewModel.state.collectAsState()

	LocationItemScreen(
		navigator = navigator,
		state = state,
		event = viewModel.event,
		onIntent = viewModel::onIntent
	)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LocationItemScreen(
	navigator: LocationNavigator,
	state: LocationItemState,
	event: Flow<LocationItemEvent>,
	onIntent: (LocationItemIntent) -> Unit
) {
	val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
	val snackbarHostState = remember { SnackbarHostState() }

	Scaffold(
		modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
		topBar = {
			Toolbar(
				scrollBehavior = scrollBehavior,
				onBack = { onIntent(LocationItemIntent.NavigateBack) }
			)
		},
		snackbarHost = { SnackbarHost(snackbarHostState) }
	) { innerPadding ->
		Column(
			modifier = Modifier
				.padding(innerPadding)
				.fillMaxSize()
		) {
			if (state.locationState.error) {
				AppErrorScreen(onClick = { onIntent(LocationItemIntent.Refresh) })
			} else {
				LazyColumn {
					item {
						if (state.locationState.skeleton) {
							LocationItemInfoSkeleton()
						} else {
							val location = state.locationState.location
							checkNotNull(location)
							LocationItemInfoContent(
								name = location.name,
								type = location.type,
								dimension = location.dimension,
								amountResidents = location.amountResidents
							)
						}
					}


					if (state.residentState.skeleton) {
						item {
							AppSpacer(height = 16.dp)

							ResidentListSkeleton()
						}
					} else {
						ResidentListContent(
							state = state.residentState,
							onIntent = onIntent
						)
					}
				}
			}

		}
	}

	event.collectAsEffect { e ->
		when (e) {
			LocationItemEvent.NavigateBack -> navigator.back()
			LocationItemEvent.ErrorUploadResidents -> {
				snackbarHostState.showSnackbar(
					message = getString(Res.string.location_error_upload_residents)
				)
			}
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Toolbar(
	scrollBehavior: TopAppBarScrollBehavior,
	onBack: () -> Unit
) {
	TopAppBar(
		title = {},
		navigationIcon = { AppToolbarNavBackIcon(onClick = onBack) },
		scrollBehavior = scrollBehavior
	)
}
