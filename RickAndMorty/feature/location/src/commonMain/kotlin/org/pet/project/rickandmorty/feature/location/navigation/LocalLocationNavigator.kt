package org.pet.project.rickandmorty.feature.location.navigation

import androidx.compose.runtime.staticCompositionLocalOf

val LocalLocationNavigator = staticCompositionLocalOf<LocationNavigator> {
    error("LocationNavigator not provided")
}