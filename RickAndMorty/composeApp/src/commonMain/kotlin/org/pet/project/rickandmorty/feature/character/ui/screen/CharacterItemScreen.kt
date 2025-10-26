package org.pet.project.rickandmorty.feature.character.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import org.pet.project.rickandmorty.core.navigation.back
import org.pet.project.rickandmorty.design.component.AppErrorScreen
import org.pet.project.rickandmorty.design.component.AppLoadingScreen
import org.pet.project.rickandmorty.feature.character.presentation.intent.CharacterItemIntent
import org.pet.project.rickandmorty.feature.character.presentation.state.CharacterItemState
import org.pet.project.rickandmorty.feature.character.presentation.viewmodel.CharacterItemViewModel
import org.pet.project.rickandmorty.feature.character.ui.view.CharacterItemContentView

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

        state.loading -> AppLoadingScreen()

        state.error -> AppErrorScreen(
            onClick = { onIntent(CharacterItemIntent.Refresh) }
        )
    }

}
