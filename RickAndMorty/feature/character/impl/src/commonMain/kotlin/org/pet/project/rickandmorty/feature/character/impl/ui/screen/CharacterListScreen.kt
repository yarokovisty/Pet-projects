package org.pet.project.rickandmorty.feature.character.impl.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import kotlinx.coroutines.flow.Flow
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.pet.project.rickandmorty.design.component.AppErrorScreen
import org.pet.project.rickandmorty.design.component.AppSnackbar
import org.pet.project.rickandmorty.design.component.AppTitleToolbar
import org.pet.project.rickandmorty.feature.character.impl.navigation.CharacterListNavigator
import org.pet.project.rickandmorty.feature.character.impl.navigation.LocalCharacterListNavigator
import org.pet.project.rickandmorty.feature.character.impl.presentation.event.CharacterListEvent
import org.pet.project.rickandmorty.feature.character.impl.presentation.intent.CharacterListIntent
import org.pet.project.rickandmorty.feature.character.impl.presentation.state.CharacterListState
import org.pet.project.rickandmorty.feature.character.impl.presentation.viewmodel.CharacterListViewModel
import org.pet.project.rickandmorty.feature.character.impl.ui.view.CharacterListContent
import org.pet.project.rickandmorty.util.collectAsEffect
import org.pet.project.rickandmorty.util.onReachEnd
import rickandmorty.feature.character.impl.generated.resources.Res
import rickandmorty.feature.character.impl.generated.resources.character_list_toolbar_title

@Composable
internal fun CharacterListScreen() {
    val navigator = LocalCharacterListNavigator.current
    val viewModel = koinViewModel<CharacterListViewModel>()
    val state by viewModel.state.collectAsState()

    CharacterListScreen(
        navigator = navigator,
        state = state,
        event = viewModel.event,
        onIntent = viewModel::onIntent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CharacterListScreen(
    navigator: CharacterListNavigator,
    state: CharacterListState,
    event: Flow<CharacterListEvent>,
    onIntent: (CharacterListIntent) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val lazyListState = rememberLazyGridState()
    val snackbarHostState = remember { SnackbarHostState() }

    lazyListState.onReachEnd(
        action = { onIntent(CharacterListIntent.Upload) }
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)) {
            Toolbar(scrollBehavior)

            if (state.error) {
                AppErrorScreen(
                    onClick = { onIntent(CharacterListIntent.Refresh) }
                )
            } else {
                CharacterListContent(
                    lazyListState = lazyListState,
                    state = state,
                    onClickCharacter = {
                        onIntent(CharacterListIntent.OpenCharacterScreen(it.id))
                    },
                    modifier = Modifier.weight(1f)
                )
            }

            if (state.uploading) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Toolbar(
    scrollBehavior: TopAppBarScrollBehavior,
) {
    TopAppBar(
        title = { AppTitleToolbar(stringResource(Res.string.character_list_toolbar_title)) },
        scrollBehavior = scrollBehavior
    )
}