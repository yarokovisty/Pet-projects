package org.pet.project.rickandmorty.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.pet.project.rickandmorty.app.ui.screen.MainScreen
import org.pet.project.rickandmorty.core.navigation.back
import org.pet.project.rickandmorty.feature.character.navigation.CharacterGraph
import org.pet.project.rickandmorty.feature.character.navigation.CharacterItemRoute
import org.pet.project.rickandmorty.feature.character.navigation.CharacterNavigator
import org.pet.project.rickandmorty.feature.character.navigation.characterGraph
import org.pet.project.rickandmorty.feature.location.navigation.LocationInfoRoute

@Composable
fun GlobalNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = MainRoute
    ) {
        composable<MainRoute> {
            MainScreen()
        }
        composable<LocationInfoRoute> {

        }
    }
}

@Composable
fun InnerNavigationGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val characterNavigator = remember { CharacterNavigatorImpl(navController) }

    NavHost(
        navController = navController,
        startDestination = CharacterGraph,
        modifier = modifier
    ) {
        characterGraph(characterNavigator)
    }
}

class CharacterNavigatorImpl(
    override val navController: NavHostController
) : CharacterNavigator {
    override fun openCharacterItemScreen(characterId: Int) {
        navController.navigate(CharacterItemRoute(characterId))
    }

    override fun openLocationScreen(locationName: String) {
        TODO("Not yet implemented")
    }

    override fun back() {
        navController.back()
    }
}
