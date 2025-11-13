package org.pet.project.rickandmorty.feature.location.navigation

import kotlinx.serialization.Serializable
import org.pet.project.rickandmorty.core.navigation.Route

@Serializable
internal data class LocationItemRoute(val name: String) : Route

