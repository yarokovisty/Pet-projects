package org.pet.project.rickandmorty.app.presentation.intent

import org.pet.project.rickandmorty.common.presentation.Intent
import org.pet.project.rickandmorty.core.navigation.destination.Tab

sealed interface MainIntent : Intent {
    class OpenAppTab(val tab: Tab) : MainIntent
}