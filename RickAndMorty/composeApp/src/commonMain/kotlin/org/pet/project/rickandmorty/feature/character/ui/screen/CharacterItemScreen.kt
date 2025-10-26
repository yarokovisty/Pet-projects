package org.pet.project.rickandmorty.feature.character.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import org.pet.project.rickandmorty.core.navigation.back
import org.pet.project.rickandmorty.design.component.AppErrorScreen
import org.pet.project.rickandmorty.feature.character.domain.entity.Character
import org.pet.project.rickandmorty.feature.character.presentation.intent.CharacterItemIntent
import org.pet.project.rickandmorty.feature.character.presentation.state.CharacterItemState
import org.pet.project.rickandmorty.feature.character.presentation.viewmodel.CharacterItemViewModel
import org.pet.project.rickandmorty.feature.character.ui.view.CharacterItemToolbar

@Composable
internal fun CharacterItemScreen(
    id: Int,
    navController: NavHostController
) {
    val viewModel = koinViewModel<CharacterItemViewModel> { parametersOf(id) }
    val state by viewModel.state.collectAsState()

    CharacterItemScreen(
        navController = navController,
        state = state,
        onIntent = viewModel::onIntent
    )
}

@Composable
private fun CharacterItemScreen(
    navController: NavHostController,
    state: CharacterItemState,
    onIntent: (CharacterItemIntent) -> Unit
) {
    val back = { navController.back() }
    when {
        state.character != null -> CharacterItemContentView(
            character = state.character,
            onBack = back
        )

        state.loading -> CharacterItemLoadingView()

        state.error -> AppErrorScreen(
            onClick = { onIntent(CharacterItemIntent.Refresh) }
        )
    }

}

@Composable
private fun CharacterItemContentView(
    character: Character,
    onBack: () -> Unit
) {
    Column {
        CharacterItemToolbar(
            characterName = character.name,
            characterGender = character.gender,
            onBack = onBack
        )
    }
}

@Composable
private fun CharacterItemLoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

