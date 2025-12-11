package org.pet.project.rickandmorty.feature.character.impl.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import org.koin.compose.viewmodel.koinViewModel
import org.pet.project.rickandmorty.design.component.AppSpacer
import org.pet.project.rickandmorty.feature.character.impl.navigation.CharacterSearchNavigator
import org.pet.project.rickandmorty.feature.character.impl.navigation.LocalCharacterSearchNavigator
import org.pet.project.rickandmorty.feature.character.impl.presentation.event.CharacterSearchEvent
import org.pet.project.rickandmorty.feature.character.impl.presentation.intent.CharacterSearchIntent
import org.pet.project.rickandmorty.feature.character.impl.presentation.state.CharacterSearchState
import org.pet.project.rickandmorty.feature.character.impl.presentation.viewmodel.CharacterSearchViewModel
import org.pet.project.rickandmorty.feature.character.impl.ui.view.CharacterSearchBar
import org.pet.project.rickandmorty.feature.character.impl.ui.view.CharacterSearchContent
import org.pet.project.rickandmorty.feature.character.impl.ui.view.CharacterSearchToolbar
import org.pet.project.rickandmorty.util.observe

@Composable
internal fun CharacterSearchScreen() {
    val viewModel = koinViewModel<CharacterSearchViewModel>()
    val state by viewModel.state.collectAsState()
    val event = viewModel.event
    val navigator = LocalCharacterSearchNavigator.current

    CharacterSearchScreen(
        state = state,
        event = event,
        navigator = navigator,
        onIntent = viewModel::onIntent
    )
}

@Composable
private fun CharacterSearchScreen(
    state: CharacterSearchState,
    event: Flow<CharacterSearchEvent>,
    navigator: CharacterSearchNavigator,
    onIntent: (CharacterSearchIntent) -> Unit,
) {
    Column {
        CharacterSearchToolbar(state = state.filterMenuState, onIntent = onIntent)

        AppSpacer(height = 8.dp)

        CharacterSearchBar(state = state.searchInputState, onIntent = onIntent)

        CharacterSearchContent(state = state.searchResultState, onIntent = onIntent)
    }

    event observe { e ->
        when (e) {
            is CharacterSearchEvent.OpenCharacterScreen ->
                navigator.openCharacterItemScreen(e.characterId)
        }
    }
}