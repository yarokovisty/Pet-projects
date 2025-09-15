package org.pet.project.rickandmorty.app

import androidx.compose.ui.window.ComposeUIViewController
import org.pet.project.rickandmorty.app.ui.screen.App
import org.pet.project.rickandmorty.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) {
    App()
}