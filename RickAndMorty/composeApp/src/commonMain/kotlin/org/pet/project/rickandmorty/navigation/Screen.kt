package org.pet.project.rickandmorty.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {

    @Serializable
    object Characters : Screen

    @Serializable
    object Episodes : Screen

    @Serializable
    object Search : Screen
}