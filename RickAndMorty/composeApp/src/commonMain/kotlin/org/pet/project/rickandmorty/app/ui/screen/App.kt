package org.pet.project.rickandmorty.app.ui.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.pet.project.rickandmorty.app.navigation.NavigationGraph
import org.pet.project.rickandmorty.app.presentation.intent.AppIntent
import org.pet.project.rickandmorty.app.presentation.viewmodel.AppViewModel
import org.pet.project.rickandmorty.app.presentation.state.AppState
import org.pet.project.rickandmorty.app.ui.view.AppNavigationBar
import org.pet.project.rickandmorty.core.navigation.rememberNavigationState
import org.pet.project.rickandmorty.feature.character.navigation.CharacterDestination
import org.pet.project.rickandmorty.feature.episode.navigation.EpisodeDestination
import org.pet.project.rickandmorty.feature.search.navigation.SearchDestination

@Composable
fun App() {
    val viewModel = viewModel { AppViewModel() }
    val state by viewModel.state.collectAsState()

    RickAndMortyApp(state) { viewModel.onIntent(it) }
}

@Composable
private fun RickAndMortyApp(
    state: AppState,
    onIntent: (AppIntent) -> Unit
) {
    val navigationState = rememberNavigationState()

    MaterialTheme {
        Scaffold(
            bottomBar = {
                AppNavigationBar(state.selectedIndexScreen) { position ->
                    onIntent(AppIntent(position))
                    val destination = when(position) {
                        0 -> CharacterDestination
                        1 -> EpisodeDestination
                        2 -> SearchDestination
                        else -> throw IllegalStateException("Illegal position $position")
                    }
                    navigationState.navigateTo(destination)
                }
            }
        ) { paddingValues ->
            val customPadding = PaddingValues(
                start = paddingValues.calculateLeftPadding(LayoutDirection.Ltr),
                top = 0.dp,
                bottom = paddingValues.calculateBottomPadding(),
                end = paddingValues.calculateRightPadding(LayoutDirection.Ltr)
            )

            NavigationGraph(
                navController = navigationState.navHostController,
                modifier = Modifier.padding(customPadding)
            )
        }
    }
}




