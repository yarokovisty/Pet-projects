package org.pet.project.rickandmorty.feature.location.impl.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import org.pet.project.rickandmorty.core.navigation.GlobalNavController
import org.pet.project.rickandmorty.core.navigation.LocalGlobalNavController

interface LocationNavigator {

    fun back()
}

class LocationNavigatorImpl(
    private val globalNavController: GlobalNavController
) : LocationNavigator {

    override fun back() {
        globalNavController.back()
    }
}

val LocalLocationNavigator = staticCompositionLocalOf<LocationNavigator> {
    error("LocationNavigator not provided")
}

@Composable
fun rememberLocationNavigator() : LocationNavigator {
    val globalNavController = LocalGlobalNavController.current
    return remember { LocationNavigatorImpl(globalNavController) }
}