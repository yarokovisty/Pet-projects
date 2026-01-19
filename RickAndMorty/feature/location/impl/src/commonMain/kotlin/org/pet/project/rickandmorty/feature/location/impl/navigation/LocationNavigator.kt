package org.pet.project.rickandmorty.feature.location.impl.navigation

import org.pet.project.rickandmorty.core.navigation.navigator.GlobalNavigator
import org.pet.project.rickandmorty.core.navigation.navigator.Navigator
import org.pet.project.rickandmorty.navigation.ksp.annotation.ScreenNavigator

@ScreenNavigator
internal class LocationNavigator(private val globalNavController: GlobalNavigator) : Navigator {

    fun back() {
        globalNavController.back()
    }
}