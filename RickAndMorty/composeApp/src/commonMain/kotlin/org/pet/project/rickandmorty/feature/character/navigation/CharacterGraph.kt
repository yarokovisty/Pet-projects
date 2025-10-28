package org.pet.project.rickandmorty.feature.character.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import org.pet.project.rickandmorty.core.navigation.Route
import org.pet.project.rickandmorty.feature.character.ui.screen.CharacterItemScreen
import org.pet.project.rickandmorty.feature.character.ui.screen.CharacterListScreen

fun NavGraphBuilder.characterGraph(
    navigator: CharacterNavigator,
    startRoute: Route = CharacterListRoute
) =
    navigation<CharacterGraph>(
        startDestination = startRoute
    ) {
        composable<CharacterListRoute> {
            CharacterListScreen(navigator)
        }
        composable<CharacterItemRoute> { backStackEntry  ->
            val characterId = backStackEntry.toRoute<CharacterItemRoute>().id
            CharacterItemScreen(
                id = characterId,
                navigator = navigator
            )
        }
    }

