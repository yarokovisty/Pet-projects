package org.pet.project.rickandmorty.app.navigation.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import org.pet.project.rickandmorty.feature.location.navigation.LocationNavigator

class LocationNavigatorImpl(
    private val globalNavController: NavHostController
) : LocationNavigator {

    override fun back() {
        globalNavController.popBackStack()
    }
}

@Composable
fun rememberLocationNavigator(
    globalNavController: NavHostController
) : LocationNavigator {
    return remember { LocationNavigatorImpl(globalNavController) }
}
