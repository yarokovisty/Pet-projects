package org.pet.project.rickandmorty.app.presentation.event

import org.pet.project.rickandmorty.common.presentation.Event
import org.pet.project.rickandmorty.core.navigation.destination.Tab

sealed interface MainEvent : Event {
    class OpenAppTab(val tab: Tab) : MainEvent
}