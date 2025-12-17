package org.pet.project.rickandmorty.feature.character.impl.presentation.intent

import org.pet.project.rickandmorty.common.presentation.Intent
import org.pet.project.rickandmorty.feature.character.impl.presentation.state.FilterState

internal sealed interface CharacterSearchIntent : Intent {
    object Clear : CharacterSearchIntent
    class ClickFilter(val filterToggle: FilterState) : CharacterSearchIntent
    class OpenCharacter(val characterId: Int) : CharacterSearchIntent
    object Refresh : CharacterSearchIntent
    class Search(val name: String) : CharacterSearchIntent
    object ToggleFilterMenu : CharacterSearchIntent
}