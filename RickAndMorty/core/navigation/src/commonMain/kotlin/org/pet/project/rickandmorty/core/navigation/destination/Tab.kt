package org.pet.project.rickandmorty.core.navigation.destination

interface Tab {
    val route: String? get() = this::class.qualifiedName
}