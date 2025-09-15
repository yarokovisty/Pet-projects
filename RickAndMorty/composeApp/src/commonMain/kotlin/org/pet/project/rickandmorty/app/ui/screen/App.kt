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
import org.pet.project.rickandmorty.app.presentation.intent.AppIntent
import org.pet.project.rickandmorty.app.presentation.viewmodel.AppViewModel
import org.pet.project.rickandmorty.app.presentation.state.AppState
import org.pet.project.rickandmorty.app.ui.view.AppNavigationBar
import org.pet.project.rickandmorty.feature.character.ui.screen.CharacterListScreen
import org.pet.project.rickandmorty.navigation.NavigationGraph
import org.pet.project.rickandmorty.navigation.rememberNavigationState

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
                AppNavigationBar(state.selectedIndexScreen) { selectedIndex ->
                    onIntent(AppIntent(selectedIndex))
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
                modifier = Modifier.padding(customPadding),
                characterListScreen = { CharacterListScreen() }

            )
        }
    }
}




