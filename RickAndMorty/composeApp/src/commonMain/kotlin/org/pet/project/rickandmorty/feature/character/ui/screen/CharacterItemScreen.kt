package org.pet.project.rickandmorty.feature.character.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import org.pet.project.rickandmorty.core.navigation.back
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
        navController,
        state
    )
}

@Composable
private fun CharacterItemScreen(
    navController: NavHostController,
    state: CharacterItemState
) {
    Column {
        CharacterItemToolbar(
            characterName = state.character.name,
            characterGender = state.character.gender,
            onBack = { navController.back() }
        )
    }
}
