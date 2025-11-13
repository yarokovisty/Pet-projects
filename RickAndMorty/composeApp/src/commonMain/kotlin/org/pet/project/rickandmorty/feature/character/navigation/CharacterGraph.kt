package org.pet.project.rickandmorty.feature.character.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import org.pet.project.rickandmorty.feature.character.ui.screen.CharacterItemScreen
import org.pet.project.rickandmorty.feature.character.ui.screen.CharacterListScreen

fun NavGraphBuilder.characterGraph() {
    navigation<CharacterTab>(CharacterListRoute) {
        composable<CharacterListRoute> {
            CharacterListScreen()
        }
        composable<CharacterItemRoute> { backStackEntry  ->
            val characterId = backStackEntry.toRoute<CharacterItemRoute>().id
            CharacterItemScreen(characterId)
        }
    }

}