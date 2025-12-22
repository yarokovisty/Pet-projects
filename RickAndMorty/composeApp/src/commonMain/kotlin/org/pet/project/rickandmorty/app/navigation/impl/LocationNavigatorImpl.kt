package org.pet.project.rickandmorty.app.navigation.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.pet.project.rickandmorty.core.navigation.GlobalNavController
import org.pet.project.rickandmorty.core.navigation.LocalGlobalNavController
import org.pet.project.rickandmorty.feature.location.impl.navigation.LocationNavigator

class LocationNavigatorImpl(
    private val globalNavController: GlobalNavController
) : LocationNavigator {

    override fun back() {
        globalNavController.back()
    }
}

@Composable
fun rememberLocationNavigator() : LocationNavigator {
    val globalNavController = LocalGlobalNavController.current
    return remember { LocationNavigatorImpl(globalNavController) }
}
