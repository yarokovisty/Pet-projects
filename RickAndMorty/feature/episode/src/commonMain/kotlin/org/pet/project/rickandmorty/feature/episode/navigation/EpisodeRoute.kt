package org.pet.project.rickandmorty.feature.episode.navigation

import kotlinx.serialization.Serializable
import org.pet.project.rickandmorty.core.navigation.Route
import org.pet.project.rickandmorty.core.navigation.Tab

@Serializable
object EpisodeTab : Tab

@Serializable
object AllEpisodeRoute : Route

@Serializable
data class CharacterEpisodeRoute(val characterId: Int)