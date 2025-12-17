package org.pet.project.rickandmorty.feature.character.impl.presentation.event

import org.pet.project.rickandmorty.common.presentation.Event
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Character

internal sealed interface CharacterItemEvent : Event {
    object Back : CharacterItemEvent
    class OpenLocationScreen(val name: String) : CharacterItemEvent
    class OpenCharacterEpisodeScreen(val character: Character) : CharacterItemEvent
}