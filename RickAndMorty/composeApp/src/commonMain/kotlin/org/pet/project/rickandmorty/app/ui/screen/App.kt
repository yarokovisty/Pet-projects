package org.pet.project.rickandmorty.app.ui.screen

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import org.pet.project.rickandmorty.app.navigation.GlobalNavGraph
import org.pet.project.rickandmorty.core.navigation.LocalGlobalNavController
import org.pet.project.rickandmorty.core.navigation.rememberGlobalNavController
import org.pet.project.rickandmorty.design.theme.colorScheme

@Composable
fun App() {
    val globalNavController = rememberGlobalNavController()

    MaterialTheme(colorScheme = colorScheme) {
        CompositionLocalProvider(LocalGlobalNavController provides globalNavController) {
            GlobalNavGraph()
        }
    }
}

