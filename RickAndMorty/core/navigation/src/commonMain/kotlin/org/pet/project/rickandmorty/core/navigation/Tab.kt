package org.pet.project.rickandmorty.core.navigation

interface Tab {
    val route: String?
        get() = this::class.qualifiedName
}