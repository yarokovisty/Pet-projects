package org.pet.project.rickandmorty.feature.episode.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.Flow
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import org.pet.project.rickandmorty.design.component.AppErrorScreen
import org.pet.project.rickandmorty.design.component.AppLoadingScreen
import org.pet.project.rickandmorty.design.component.AppTitleToolbar
import org.pet.project.rickandmorty.design.component.AppToolbarNavBackIcon
import org.pet.project.rickandmorty.feature.episode.navigation.CharacterEpisodeNavigator
import org.pet.project.rickandmorty.feature.episode.navigation.LocalCharacterEpisodeNavigator
import org.pet.project.rickandmorty.feature.episode.presentation.event.CharacterEpisodeEvent
import org.pet.project.rickandmorty.feature.episode.presentation.intent.CharacterEpisodeIntent
import org.pet.project.rickandmorty.feature.episode.presentation.state.CharacterEpisodeState
import org.pet.project.rickandmorty.feature.episode.presentation.viewmodel.CharacterEpisodeViewModel
import org.pet.project.rickandmorty.feature.episode.ui.view.CharacterEpisodeContent
import org.pet.project.rickandmorty.util.collectAsEffect

private typealias CharacterId = Int

@Composable
internal fun CharacterEpisodeScreen(id: CharacterId) {
    val navigator = LocalCharacterEpisodeNavigator.current
    val viewModel = koinViewModel<CharacterEpisodeViewModel> { parametersOf(id) }
    val state by viewModel.state.collectAsState()

    CharacterEpisodeScreen(
        navigator = navigator,
        state = state,
        event = viewModel.event,
        onIntent = viewModel::onIntent
    )
}

@Composable
private fun CharacterEpisodeScreen(
    navigator: CharacterEpisodeNavigator,
    state: CharacterEpisodeState,
    event: Flow<CharacterEpisodeEvent>,
    onIntent: (CharacterEpisodeIntent) -> Unit,
) {
    Scaffold(
        topBar = {
            Toolbar(
                title = state.character.name,
                onClick = { onIntent(CharacterEpisodeIntent.Back) }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                state.loading -> AppLoadingScreen()

                state.error -> AppErrorScreen(
                    onClick = { onIntent(CharacterEpisodeIntent.Refresh) }
                )

                else -> CharacterEpisodeContent(
                    seasons = state.seasons,
                    characterImage = state.character.image
                )
            }
        }

        event.collectAsEffect { e ->
            when(e) {
                CharacterEpisodeEvent.Back -> navigator.back()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Toolbar(
    title: String,
    onClick: () -> Unit,
) {
    TopAppBar(
        title = { AppTitleToolbar(title) },
        navigationIcon = { AppToolbarNavBackIcon(onClick) }
    )
}