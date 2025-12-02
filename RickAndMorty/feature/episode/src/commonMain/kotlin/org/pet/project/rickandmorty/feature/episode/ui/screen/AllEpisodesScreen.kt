package org.pet.project.rickandmorty.feature.episode.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.viewmodel.koinViewModel
import org.pet.project.rickandmorty.design.component.AppErrorScreen
import org.pet.project.rickandmorty.design.component.AppLoadingScreen
import org.pet.project.rickandmorty.design.component.AppTitleToolbar
import org.pet.project.rickandmorty.feature.episode.presentation.intent.AllEpisodesIntent
import org.pet.project.rickandmorty.feature.episode.presentation.state.AllEpisodesState
import org.pet.project.rickandmorty.feature.episode.presentation.viewmodel.AllEpisodesViewModel
import org.pet.project.rickandmorty.feature.episode.ui.view.AllEpisodesContent

@Composable
internal fun AllEpisodesScreen() {
    val viewModel = koinViewModel<AllEpisodesViewModel>()
    val state by viewModel.state.collectAsState()

    AllEpisodesScreen(
        state = state,
        onIntent = viewModel::onIntent
    )
}

@Composable
private fun AllEpisodesScreen(
    state: AllEpisodesState,
    onIntent: (AllEpisodesIntent) -> Unit
) {
    val lazyListState = rememberLazyGridState()

    Column {
        Toolbar()

        when {
            state.loading -> AppLoadingScreen()

            state.error -> AppErrorScreen(
                onClick = { onIntent(AllEpisodesIntent.Refresh) }
            )

            else -> AllEpisodesContent(
                lazyListState = lazyListState,
                state = state
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Toolbar() {
    TopAppBar(
        title = { AppTitleToolbar("All Episodes") }
    )
}