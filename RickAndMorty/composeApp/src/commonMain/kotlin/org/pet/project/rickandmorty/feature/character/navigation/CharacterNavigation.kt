package org.pet.project.rickandmorty.feature.character.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import org.pet.project.rickandmorty.core.navigation.Destination
import org.pet.project.rickandmorty.core.navigation.Route
import org.pet.project.rickandmorty.feature.character.ui.screen.CharacterItemScreen
import org.pet.project.rickandmorty.feature.character.ui.screen.CharacterListScreen

@Serializable object CharacterDestination : Destination

@Serializable internal object CharacterListRoute : Route

@Serializable internal class CharacterItemRoute(val id: Int) : Route

fun NavGraphBuilder.characterFlow(navController: NavHostController) =
    navigation<CharacterDestination>(
        startDestination = CharacterListRoute
    ) {
        composable<CharacterListRoute> {
            CharacterListScreen(navController)
        }
        composable<CharacterItemRoute> { backStackEntry  ->
            val characterId = backStackEntry.toRoute<CharacterItemRoute>().id
            CharacterItemScreen(
                characterId,
                navController
            )
        }
    }

