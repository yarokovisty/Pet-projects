package org.pet.project.rickandmorty.feature.character.navigation

import kotlinx.serialization.Serializable
import org.pet.project.rickandmorty.core.navigation.Route
import org.pet.project.rickandmorty.core.navigation.Tab

@Serializable object CharacterTab : Tab

@Serializable internal object CharacterListRoute : Route

@Serializable internal data class CharacterItemRoute(val id: Int) : Route