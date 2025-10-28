package org.pet.project.rickandmorty.feature.location.navigation

import kotlinx.serialization.Serializable
import org.pet.project.rickandmorty.core.navigation.Graph
import org.pet.project.rickandmorty.core.navigation.Route

@Serializable
object LocationGraph : Graph

@Serializable
data class LocationInfoRoute(val name: Int) : Route

