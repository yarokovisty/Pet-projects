package org.pet.project.rickandmorty.feature.location.impl.navigation

import androidx.compose.runtime.staticCompositionLocalOf
import org.pet.project.rickandmorty.core.navigation.navigator.GlobalNavigator
import org.pet.project.rickandmorty.core.navigation.navigator.Navigator

class LocationNavigator(private val globalNavController: GlobalNavigator) : Navigator {

    fun back() {
        globalNavController.back()
    }
}

val LocalLocationNavigator = staticCompositionLocalOf<LocationNavigator> {
    error("LocationNavigator not provided")
}