package org.pet.project.rickandmorty.feature.location.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import org.pet.project.rickandmorty.feature.location.ui.screen.LocationItemScreen

fun NavGraphBuilder.locationScreen() {
    composable<LocationItemRoute> { backEntry ->
        val locationName = backEntry.toRoute<LocationItemRoute>().name
        LocationItemScreen(locationName)
    }
}