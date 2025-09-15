package org.pet.project.rickandmorty

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.pet.project.rickandmorty.app.ui.screen.App
import org.pet.project.rickandmorty.di.initKoin

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "RickAndMorty",
    ) {
        initKoin()
        App()
    }
}