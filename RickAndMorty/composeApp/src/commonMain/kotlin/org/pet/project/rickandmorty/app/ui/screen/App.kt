package org.pet.project.rickandmorty.app.ui.screen

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import org.pet.project.rickandmorty.app.navigation.GlobalNavGraph
import org.pet.project.rickandmorty.design.theme.colorScheme

@Composable
fun App() {
    val navController = rememberNavController()

    MaterialTheme(colorScheme = colorScheme) {
        GlobalNavGraph(navController)
    }
}

