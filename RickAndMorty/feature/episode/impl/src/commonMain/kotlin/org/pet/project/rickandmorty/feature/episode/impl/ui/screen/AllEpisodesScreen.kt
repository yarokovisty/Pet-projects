package org.pet.project.rickandmorty.feature.episode.impl.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.Flow
import org.koin.compose.viewmodel.koinViewModel
import org.pet.project.rickandmorty.design.component.AppErrorScreen
import org.pet.project.rickandmorty.design.component.AppLoadingScreen
import org.pet.project.rickandmorty.design.component.AppSnackbar
import org.pet.project.rickandmorty.design.component.AppTitleToolbar
import org.pet.project.rickandmorty.feature.episode.impl.presentation.event.AllEpisodesEvent
import org.pet.project.rickandmorty.feature.episode.impl.presentation.intent.AllEpisodesIntent
import org.pet.project.rickandmorty.feature.episode.impl.presentation.state.AllEpisodesState
import org.pet.project.rickandmorty.feature.episode.impl.presentation.viewmodel.AllEpisodesViewModel
import org.pet.project.rickandmorty.feature.episode.impl.ui.view.AllEpisodesContent
import org.pet.project.rickandmorty.util.observe
import org.pet.project.rickandmorty.util.onReachEnd

@Composable
internal fun AllEpisodesScreen() {
    val viewModel = koinViewModel<AllEpisodesViewModel>()
    val state by viewModel.state.collectAsState()
    val event = viewModel.event

    AllEpisodesScreen(
        state = state,
        event = event,
        onIntent = viewModel::onIntent
    )
}

@Composable
private fun AllEpisodesScreen(
    state: AllEpisodesState,
    event: Flow<AllEpisodesEvent>,
    onIntent: (AllEpisodesIntent) -> Unit
) {
    val lazyListState = rememberLazyGridState()
    val snackbarHostState = remember { SnackbarHostState() }

    lazyListState.onReachEnd(
        action = { onIntent(AllEpisodesIntent.Upload) }
    )

    Box {
        Column {
            Toolbar()

            when {
                state.loading -> AppLoadingScreen()

                state.error -> AppErrorScreen(
                    onClick = { onIntent(AllEpisodesIntent.Refresh) }
                )

                else -> AllEpisodesContent(
                    lazyListState = lazyListState,
                    episodes = state.episodes
                )
            }
        }

        if (state.uploading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            )
        }

        AppSnackbar(snackbarHostState)
    }


    event observe { e ->
        when(e) {
            is AllEpisodesEvent.OnReachEnd -> {
                snackbarHostState.showSnackbar(
                    message = e.message,
                    duration = SnackbarDuration.Long
                )
            }

            is AllEpisodesEvent.ErrorUploadEpisodes -> {
                snackbarHostState.showSnackbar(
                    message = e.message,
                    duration = SnackbarDuration.Long
                )
            }
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