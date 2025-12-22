package org.pet.project.rickandmorty.feature.location.impl.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import org.pet.project.rickandmorty.feature.location.api.navigation.LocationItemRoute
import org.pet.project.rickandmorty.feature.location.impl.ui.screen.LocationItemScreen

fun NavGraphBuilder.locationScreen() {
    composable<LocationItemRoute> { backStackEntry ->
        val locationName = backStackEntry.toRoute<LocationItemRoute>().name
        LocationItemScreen(locationName)
    }
}