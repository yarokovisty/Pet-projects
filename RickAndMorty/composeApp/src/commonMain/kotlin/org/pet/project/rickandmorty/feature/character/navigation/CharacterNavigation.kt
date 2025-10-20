package org.pet.project.rickandmorty.feature.character.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import kotlinx.serialization.Serializable
import org.pet.project.rickandmorty.core.navigation.Destination
import org.pet.project.rickandmorty.core.navigation.Screen
import org.pet.project.rickandmorty.feature.character.ui.screen.CharacterListScreen

@Serializable object CharacterDestination : Destination

@Serializable internal object Characters : Screen

fun NavGraphBuilder.characterFlow(navController: NavHostController) =
    navigation<CharacterDestination>(
        startDestination = Characters
    ) {
        composable<Characters> {
            CharacterListScreen()
        }
    }

