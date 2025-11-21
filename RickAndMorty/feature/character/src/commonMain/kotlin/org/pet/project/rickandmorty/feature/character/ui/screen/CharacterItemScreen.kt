package org.pet.project.rickandmorty.feature.character.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.coroutines.flow.Flow
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import org.pet.project.rickandmorty.design.component.AppErrorScreen
import org.pet.project.rickandmorty.design.component.AppLoadingScreen
import org.pet.project.rickandmorty.feature.character.navigation.CharacterNavigator
import org.pet.project.rickandmorty.feature.character.navigation.LocalCharacterNavigator
import org.pet.project.rickandmorty.feature.character.presentation.event.CharacterItemEvent
import org.pet.project.rickandmorty.feature.character.presentation.intent.CharacterItemIntent
import org.pet.project.rickandmorty.feature.character.presentation.state.CharacterItemState
import org.pet.project.rickandmorty.feature.character.presentation.viewmodel.CharacterItemViewModel
import org.pet.project.rickandmorty.feature.character.ui.view.CharacterItemContent
import org.pet.project.rickandmorty.util.collectAsEffect

private typealias CharacterId = Int

@Composable
internal fun CharacterItemScreen(id: CharacterId) {
    val navigator = LocalCharacterNavigator.current
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
    navigator: CharacterNavigator,
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

    event.collectAsEffect { e ->
        when (e) {
            is CharacterItemEvent.Back -> {
                navigator.back()
            }

            is CharacterItemEvent.OpenLocationScreen -> {
                navigator.openLocationScreen(e.name)
            }
        }
    }
}
