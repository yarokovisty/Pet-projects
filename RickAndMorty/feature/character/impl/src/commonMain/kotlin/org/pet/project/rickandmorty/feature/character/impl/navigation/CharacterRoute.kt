package org.pet.project.rickandmorty.feature.character.impl.navigation

import kotlinx.serialization.Serializable
import org.pet.project.rickandmorty.core.navigation.Route
import org.pet.project.rickandmorty.core.navigation.Tab

@Serializable object CharacterTab : Tab

@Serializable object CharacterListRoute : Route

@Serializable data class CharacterItemRoute(val id: Int) : Route