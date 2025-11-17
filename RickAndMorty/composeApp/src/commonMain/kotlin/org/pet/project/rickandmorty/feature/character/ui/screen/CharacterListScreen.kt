package org.pet.project.rickandmorty.feature.character.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.SnackbarDuration
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import org.jetbrains.compose.resources.getString
import org.koin.compose.viewmodel.koinViewModel
import org.pet.project.rickandmorty.design.component.AppErrorScreen
import org.pet.project.rickandmorty.design.component.AppSnackbar
import org.pet.project.rickandmorty.feature.character.navigation.CharacterNavigator
import org.pet.project.rickandmorty.feature.character.navigation.LocalCharacterNavigator
import org.pet.project.rickandmorty.feature.character.presentation.event.CharacterListEvent
import org.pet.project.rickandmorty.feature.character.presentation.intent.CharacterListIntent
import org.pet.project.rickandmorty.feature.character.presentation.state.CharacterListState
import org.pet.project.rickandmorty.feature.character.presentation.viewmodel.CharacterListViewModel
import org.pet.project.rickandmorty.feature.character.ui.view.CharacterListToolbar
import org.pet.project.rickandmorty.feature.character.ui.view.CharacterListView
import org.pet.project.rickandmorty.util.collectAsEffect

@Composable
internal fun CharacterListScreen() {
    val navigator = LocalCharacterNavigator.current
    val viewModel = koinViewModel<CharacterListViewModel>()
    val state by viewModel.state.collectAsState()

    CharacterListScreen(
        navigator = navigator,
        state = state,
        event = viewModel.event,
        onIntent = { viewModel.onIntent(it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CharacterListScreen(
    navigator: CharacterNavigator,
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

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)) {
            CharacterListToolbar(scrollBehavior)

            if (state.error) {
                AppErrorScreen(
                    onClick = { onIntent(CharacterListIntent.Refresh) }
                )
            } else {
                CharacterListView(
                    lazyListState = lazyListState,
                    state = state,
                    onClickCharacter = {
                        onIntent(CharacterListIntent.OpenCharacterScreen(it.id))
                    },
                    modifier = Modifier.weight(1f)
                )
            }

            if (state.isLoadingMore) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
        }

        AppSnackbar(snackbarHostState)
    }

    event.collectAsEffect { e ->
        when(e) {
            is CharacterListEvent.OpenCharacterScreen -> {
                navigator.openCharacterItemScreen(e.id)
            }

            is CharacterListEvent.Error -> {
                snackbarHostState.showSnackbar(
                    message = getString(e.errorMes),
                    duration = SnackbarDuration.Short
                )
            }
        }

    }
}