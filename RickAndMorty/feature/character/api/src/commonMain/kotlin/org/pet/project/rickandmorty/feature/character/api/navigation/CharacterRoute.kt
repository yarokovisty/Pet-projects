package org.pet.project.rickandmorty.feature.character.api.navigation

import kotlinx.serialization.Serializable
import org.pet.project.rickandmorty.core.navigation.destination.Route
import org.pet.project.rickandmorty.core.navigation.destination.Tab

@Serializable
object CharacterTab : Tab

@Serializable
object CharacterListRoute : Route

@Serializable
data class CharacterItemRoute(val id: Int) : Route

@Serializable
object CharacterSearchTab : Tab

@Serializable
object CharacterSearchRoute : Route