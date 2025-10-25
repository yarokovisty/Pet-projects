package org.pet.project.rickandmorty.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import org.pet.project.rickandmorty.feature.character.navigation.CharacterDestination
import org.pet.project.rickandmorty.feature.character.navigation.characterFlow

@Composable
fun NavigationGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = CharacterDestination,
        modifier = modifier
    ) {
        characterFlow(navController)
    }
}