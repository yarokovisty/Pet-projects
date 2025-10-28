package org.pet.project.rickandmorty.app.ui.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.pet.project.rickandmorty.app.navigation.InnerNavigationGraph
import org.pet.project.rickandmorty.app.presentation.intent.MainIntent
import org.pet.project.rickandmorty.app.presentation.state.MainState
import org.pet.project.rickandmorty.app.presentation.viewmodel.MainViewModel
import org.pet.project.rickandmorty.app.ui.view.AppNavigationBar
import org.pet.project.rickandmorty.core.navigation.navigateRootTo
import org.pet.project.rickandmorty.feature.character.navigation.CharacterGraph

@Composable
internal fun MainScreen() {
    val navController = rememberNavController()
    val viewModel = viewModel { MainViewModel() }
    val state by viewModel.state.collectAsState()

    MainScreen(
        navController = navController,
        state = state,
        onIntent = { viewModel.onIntent(it) }
    )
}

@Composable
private fun MainScreen(
    navController: NavHostController,
    state: MainState,
    onIntent: (MainIntent) -> Unit
) {
    Scaffold(
        bottomBar = {
            AppNavigationBar(state.selectedIndexScreen) { position ->
                onIntent(MainIntent(position))
                val destination = when(position) {
                    0 -> CharacterGraph
                    1 -> TODO()
                    2 -> TODO()
                    else -> throw IllegalStateException("Illegal position $position")
                }
                navController.navigateRootTo(destination)
            }
        }
    ) { paddingValues ->
        val customPadding = PaddingValues(
            start = paddingValues.calculateLeftPadding(LayoutDirection.Ltr),
            top = 0.dp,
            bottom = paddingValues.calculateBottomPadding(),
            end = paddingValues.calculateRightPadding(LayoutDirection.Ltr)
        )

        InnerNavigationGraph(
            navController = navController,
            modifier = Modifier.padding(customPadding)
        )
    }
}