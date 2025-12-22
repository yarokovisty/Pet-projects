package org.pet.project.rickandmorty.feature.location.api.navigation

import kotlinx.serialization.Serializable
import org.pet.project.rickandmorty.core.navigation.Route

@Serializable
data class LocationItemRoute(val name: String) : Route

