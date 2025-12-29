package org.pet.project.rickandmorty.app.presentation.viewmodel

import org.pet.project.rickandmorty.app.presentation.event.MainEvent
import org.pet.project.rickandmorty.app.presentation.intent.MainIntent
import org.pet.project.rickandmorty.app.presentation.state.MainState
import org.pet.project.rickandmorty.common.presentation.BaseViewModel
import org.pet.project.rickandmorty.core.navigation.destination.Tab

internal class MainViewModel : BaseViewModel<MainState, MainIntent, MainEvent>() {

    override fun initState(): MainState {
        return MainState()
    }

    override fun onIntent(intent: MainIntent) {
        when (intent) {
            is MainIntent.OpenAppTab -> openAppTab(intent.tab)
        }
    }

    private fun openAppTab(tab: Tab) {
        launchInScope {
            setEvent(MainEvent.OpenAppTab(tab))
        }
    }
}