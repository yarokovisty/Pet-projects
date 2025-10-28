package org.pet.project.rickandmorty.app.ui.screen

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import org.pet.project.rickandmorty.app.navigation.GlobalNavGraph

@Composable
fun App() {
    val navController = rememberNavController()

    MaterialTheme {
        GlobalNavGraph(navController)
    }
}

