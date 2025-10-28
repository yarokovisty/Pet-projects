package org.pet.project.rickandmorty.app.presentation.viewmodel

import org.pet.project.rickandmorty.app.presentation.state.MainState
import org.pet.project.rickandmorty.app.presentation.intent.MainIntent
import org.pet.project.rickandmorty.common.presentation.BaseViewModel

internal class MainViewModel : BaseViewModel<MainState, MainIntent, Nothing>() {

    override fun initState(): MainState {
        return MainState()
    }

    override fun onIntent(intent: MainIntent) {
        updateState { copy(selectedIndexScreen = intent.indexScreen) }
    }
}