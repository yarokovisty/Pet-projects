package org.pet.project.rickandmorty.app.presentation.state

import org.pet.project.rickandmorty.common.presentation.State

internal data class AppState(
    val selectedIndexScreen: Int = 0
) : State