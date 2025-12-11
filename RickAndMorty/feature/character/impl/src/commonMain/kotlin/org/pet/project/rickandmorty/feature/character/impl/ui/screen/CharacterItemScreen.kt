package org.pet.project.rickandmorty.feature.character.impl.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.coroutines.flow.Flow
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import org.pet.project.rickandmorty.design.component.AppErrorScreen
import org.pet.project.rickandmorty.design.component.AppLoadingScreen
import org.pet.project.rickandmorty.feature.character.impl.navigation.CharacterItemNavigator
import org.pet.project.rickandmorty.feature.character.impl.navigation.LocalCharacterItemNavigator
import org.pet.project.rickandmorty.feature.character.impl.presentation.event.CharacterItemEvent
import org.pet.project.rickandmorty.feature.character.impl.presentation.intent.CharacterItemIntent
import org.pet.project.rickandmorty.feature.character.impl.presentation.state.CharacterItemState
import org.pet.project.rickandmorty.feature.character.impl.presentation.viewmodel.CharacterItemViewModel
import org.pet.project.rickandmorty.feature.character.impl.ui.view.CharacterItemContent
import org.pet.project.rickandmorty.util.observe

private typealias CharacterId = Int

@Composable
internal fun CharacterItemScreen(id: CharacterId) {
    val navigator = LocalCharacterItemNavigator.current
    val viewModel = koinViewModel<CharacterItemViewModel> { parametersOf(id) }
    val state by viewModel.state.collectAsState()

    CharacterItemScreen(
        navigator = navigator,
        state = state,
        event = viewModel.event,
        onIntent = viewModel::onIntent
    )
}

@Composable
private fun CharacterItemScreen(
    navigator: CharacterItemNavigator,
    state: CharacterItemState,
    event: Flow<CharacterItemEvent>,
    onIntent: (CharacterItemIntent) -> Unit
) {
    when {
        state.character != null -> CharacterItemContent(
            character = state.character,
            onIntent = onIntent
        )

        state.loading -> AppLoadingScreen()

        state.error -> AppErrorScreen(
            onClick = { onIntent(CharacterItemIntent.Refresh) }
        )
    }

    event observe { e ->
        when (e) {
            is CharacterItemEvent.Back -> {
                navigator.back()
            }

            is CharacterItemEvent.OpenLocationScreen -> {
                navigator.openLocationScreen(e.name)
            }

            is CharacterItemEvent.OpenCharacterEpisodeScreen -> {
                navigator.openCharacterEpisodeScreen(e.character.id)
            }
        }
    }
}
