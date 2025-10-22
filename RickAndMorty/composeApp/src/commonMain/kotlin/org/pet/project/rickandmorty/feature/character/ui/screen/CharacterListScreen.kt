package org.pet.project.rickandmorty.feature.character.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import org.jetbrains.compose.resources.getString
import org.koin.compose.viewmodel.koinViewModel
import org.pet.project.rickandmorty.utils.collectAsEffect
import org.pet.project.rickandmorty.feature.character.presentation.event.CharacterListEvent
import org.pet.project.rickandmorty.feature.character.presentation.intent.CharacterListIntent
import org.pet.project.rickandmorty.feature.character.presentation.state.CharacterListState
import org.pet.project.rickandmorty.feature.character.presentation.viewmodel.CharacterListViewModel
import org.pet.project.rickandmorty.feature.character.ui.view.CharacterListErrorView
import org.pet.project.rickandmorty.feature.character.ui.view.CharacterListToolbar
import org.pet.project.rickandmorty.feature.character.ui.view.CharacterListUploadView
import org.pet.project.rickandmorty.feature.character.ui.view.CharacterListView

@Composable
fun CharacterListScreen() {
    val viewModel = koinViewModel<CharacterListViewModel>()
    val state by viewModel.state.collectAsState()

    CharacterListScreen(
        state = state,
        event = viewModel.event
    ) { viewModel.onIntent(it) }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CharacterListScreen(
    state: CharacterListState,
    event: Flow<CharacterListEvent>,
    onIntent: (CharacterListIntent) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val lazyListState = rememberLazyGridState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.characters) {
        snapshotFlow {
            lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
        }
            .distinctUntilChanged()
            .collect { lastVisibleIndex ->
                if (lastVisibleIndex == state.characters.lastIndex) {
                    onIntent(CharacterListIntent.Upload)
                }
            }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            CharacterListToolbar(scrollBehavior)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    start = 0.dp,
                    end = 0.dp,
                    bottom = 0.dp
                )
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            if (state.error) {
                CharacterListErrorView { onIntent(CharacterListIntent.Refresh) }
            } else {
                CharacterListView(
                    lazyListState = lazyListState,
                    state = state,
                    onClickCharacter = {
                        onIntent(CharacterListIntent.OpenCharacterScreen(it))
                    },
                    Modifier.weight(1f)
                )
            }

            if (state.isLoadingMore) {
                CharacterListUploadView()
            }
        }
    }

    event.collectAsEffect {
        snackbarHostState.showSnackbar(
            message = getString(it.errorMessage),
            duration = SnackbarDuration.Short
        )
    }
}