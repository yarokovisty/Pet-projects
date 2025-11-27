package org.pet.project.rickandmorty.feature.episode.ui.screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import org.pet.project.rickandmorty.design.component.AppToolbarNavBackIcon
import org.pet.project.rickandmorty.feature.episode.presentation.intent.CharacterEpisodeIntent
import org.pet.project.rickandmorty.feature.episode.presentation.state.CharacterEpisodeState
import org.pet.project.rickandmorty.feature.episode.presentation.viewmodel.CharacterEpisodeViewModel

private typealias CharacterId = Int

@Composable
internal fun CharacterEpisodeScreen(id: CharacterId) {
    val viewModel = koinViewModel<CharacterEpisodeViewModel> { parametersOf(id) }
    val state by viewModel.state.collectAsState()


}

@Composable
private fun CharacterEpisodeScreen(
    state: CharacterEpisodeState,
    onIntent: (CharacterEpisodeIntent) -> Unit,
) {
    Scaffold(
        topBar = {
            Toolbar(
                onClick = {  }
            )
        }
    ) { innerPadding ->

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Toolbar(
    onClick: () -> Unit,
) {
    TopAppBar(
        title = {},
        navigationIcon = { AppToolbarNavBackIcon(onClick) }
    )
}