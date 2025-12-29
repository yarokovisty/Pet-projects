package org.pet.project.rickandmorty.app.ui.screen

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import org.pet.project.rickandmorty.app.navigation.GlobalNavGraph
import org.pet.project.rickandmorty.core.navigation.navigator.LocalGlobalNavigator
import org.pet.project.rickandmorty.core.navigation.navigator.rememberGlobalNavigator
import org.pet.project.rickandmorty.design.theme.colorScheme

@Composable
fun App() {
    val globalNavController = rememberGlobalNavigator()

    MaterialTheme(colorScheme = colorScheme) {
        CompositionLocalProvider(LocalGlobalNavigator provides globalNavController) {
            GlobalNavGraph()
        }
    }
}

