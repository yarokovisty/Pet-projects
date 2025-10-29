package org.pet.project.rickandmorty.feature.location.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import org.pet.project.rickandmorty.feature.character.presentation.viewmodel.CharacterItemViewModel
import org.pet.project.rickandmorty.feature.location.presentation.intent.LocationItemIntent
import org.pet.project.rickandmorty.feature.location.presentation.state.LocationItemState

@Composable
fun LocationItemScreen(
    name: LocationName
) {
    val viewModel = koinViewModel<CharacterItemViewModel> { parametersOf(name) }
}

@Composable
private fun LocationItemScreen(
    state: LocationItemState,
    onIntent: (LocationItemIntent) -> Unit
) {

}

private typealias LocationName = String
