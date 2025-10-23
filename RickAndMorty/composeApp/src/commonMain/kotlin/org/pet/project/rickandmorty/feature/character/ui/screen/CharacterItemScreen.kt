package org.pet.project.rickandmorty.feature.character.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import org.pet.project.rickandmorty.feature.character.presentation.state.CharacterItemState
import org.pet.project.rickandmorty.feature.character.presentation.viewmodel.CharacterItemViewModel

@Composable
internal fun CharacterItemScreen(id: Int) {
    val viewModel = koinViewModel<CharacterItemViewModel> { parametersOf(id) }
    val state by viewModel.state.collectAsState()

    CharacterItemScreen(state)
}

@Composable
private fun CharacterItemScreen(state: CharacterItemState) {

    Column {
        Text("Character")
    }
}