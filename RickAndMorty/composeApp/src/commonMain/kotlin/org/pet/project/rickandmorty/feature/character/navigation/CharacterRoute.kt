package org.pet.project.rickandmorty.feature.character.navigation

import kotlinx.serialization.Serializable
import org.pet.project.rickandmorty.core.navigation.Route

@Serializable internal object CharacterListRoute : Route

@Serializable internal data class CharacterItemRoute(val id: Int) : Route