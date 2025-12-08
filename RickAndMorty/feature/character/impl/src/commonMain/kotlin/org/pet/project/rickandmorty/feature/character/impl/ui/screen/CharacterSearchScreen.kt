package org.pet.project.rickandmorty.feature.character.impl.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.pet.project.rickandmorty.design.component.AppSpacer
import org.pet.project.rickandmorty.design.component.AppTitleToolbar
import org.pet.project.rickandmorty.feature.character.impl.presentation.intent.CharacterSearchIntent
import org.pet.project.rickandmorty.feature.character.impl.presentation.state.CharacterSearchState
import org.pet.project.rickandmorty.feature.character.impl.presentation.viewmodel.CharacterSearchViewModel
import org.pet.project.rickandmorty.feature.character.impl.ui.view.CharacterSearchBar
import org.pet.project.rickandmorty.feature.character.impl.ui.view.CharacterSearchContent
import rickandmorty.feature.character.impl.generated.resources.character_search_title
import rickandmorty.feature.character.impl.generated.resources.Res as SearchRes

@Composable
internal fun CharacterSearchScreen() {
    val viewModel = koinViewModel<CharacterSearchViewModel>()
    val state by viewModel.state.collectAsState()

    CharacterSearchScreen(
        state = state,
        onIntent = viewModel::onIntent
    )
}

@Composable
private fun CharacterSearchScreen(
    state: CharacterSearchState,
    onIntent: (CharacterSearchIntent) -> Unit,
) {
    Column {
        Toolbar()

        AppSpacer(height = 8.dp)

        CharacterSearchBar(
            state = state.searchInputState,
            onIntent = onIntent
        )

        CharacterSearchContent(
            state = state.searchResultState,
            onIntent = onIntent
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Toolbar() {
    TopAppBar(
        title = {
            AppTitleToolbar(stringResource(SearchRes.string.character_search_title))
        }
    )
}



