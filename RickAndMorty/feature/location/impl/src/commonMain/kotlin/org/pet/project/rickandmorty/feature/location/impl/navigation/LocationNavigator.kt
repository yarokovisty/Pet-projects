package org.pet.project.rickandmorty.feature.location.impl.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.pet.project.rickandmorty.core.navigation.navigator.GlobalNavigator
import org.pet.project.rickandmorty.core.navigation.navigator.LocalGlobalNavigator
import org.pet.project.rickandmorty.core.navigation.navigator.Navigator

internal class LocationNavigator(private val globalNavController: GlobalNavigator) : Navigator {

    fun back() {
        globalNavController.back()
    }
}

@Composable
internal fun rememberLocationNavigator(): LocationNavigator {
    val globalNavigator = LocalGlobalNavigator.current

    return remember { LocationNavigator(globalNavigator) }
}