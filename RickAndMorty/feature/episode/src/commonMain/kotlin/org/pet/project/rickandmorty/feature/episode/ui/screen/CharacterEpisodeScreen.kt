package org.pet.project.rickandmorty.feature.episode.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
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
    onIntent: (CharacterEpisodeIntent) -> Unit
) {

}