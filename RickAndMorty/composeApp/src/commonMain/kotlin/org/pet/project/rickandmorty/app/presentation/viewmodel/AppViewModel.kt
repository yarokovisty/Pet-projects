package org.pet.project.rickandmorty.app.presentation.viewmodel

import org.pet.project.rickandmorty.app.presentation.state.AppState
import org.pet.project.rickandmorty.app.presentation.intent.AppIntent
import org.pet.project.rickandmorty.common.presentation.BaseViewModel

internal class AppViewModel : BaseViewModel<AppState, AppIntent, Nothing>() {

    override fun initState(): AppState {
        return AppState()
    }

    override fun onIntent(intent: AppIntent) {
        updateState { copy(selectedIndexScreen = intent.indexScreen) }
    }
}