package org.pet.project.rickandmorty.feature.episode.api.navigation

import kotlinx.serialization.Serializable
import org.pet.project.rickandmorty.core.navigation.destination.Route
import org.pet.project.rickandmorty.core.navigation.destination.Tab

@Serializable
object EpisodeTab : Tab

@Serializable
object AllEpisodesRoute : Route

@Serializable
data class CharacterEpisodeRoute(val characterId: Int) : Route