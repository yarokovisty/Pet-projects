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
import org.pet.project.rickandmorty.app.navigation.InnerNavGraph
import org.pet.project.rickandmorty.app.presentation.intent.MainIntent
import org.pet.project.rickandmorty.app.presentation.state.MainState
import org.pet.project.rickandmorty.app.presentation.viewmodel.MainViewModel
import org.pet.project.rickandmorty.app.ui.view.AppNavigationBar
import org.pet.project.rickandmorty.core.navigation.navigateToTab
import org.pet.project.rickandmorty.feature.character.impl.navigation.CharacterTab

@Composable
internal fun MainScreen(globalNavController: NavHostController) {
    val innerNavController = rememberNavController()
    val viewModel = viewModel { MainViewModel() }
    val state by viewModel.state.collectAsState()

    MainScreen(
        globalNavController = globalNavController,
        innerNavController = innerNavController,
        state = state,
        onIntent = { viewModel.onIntent(it) }
    )
}

@Composable
private fun MainScreen(
    globalNavController: NavHostController,
    innerNavController: NavHostController,
    state: MainState,
    onIntent: (MainIntent) -> Unit
) {
    Scaffold(
        bottomBar = {
            AppNavigationBar(state.selectedIndexScreen) { position ->
                onIntent(MainIntent(position))
                val destination = when(position) {
                    0 -> CharacterTab
                    1 -> TODO()
                    2 -> TODO()
                    else -> throw IllegalStateException("Illegal position $position")
                }
                innerNavController.navigateToTab(destination)
            }
        }
    ) { paddingValues ->
        val customPadding = PaddingValues(
            start = paddingValues.calculateLeftPadding(LayoutDirection.Ltr),
            top = 0.dp,
            bottom = paddingValues.calculateBottomPadding(),
            end = paddingValues.calculateRightPadding(LayoutDirection.Ltr)
        )

        InnerNavGraph(
            globalNavController = globalNavController,
            innerNavController = innerNavController,
            modifier = Modifier.padding(customPadding)
        )
    }
}