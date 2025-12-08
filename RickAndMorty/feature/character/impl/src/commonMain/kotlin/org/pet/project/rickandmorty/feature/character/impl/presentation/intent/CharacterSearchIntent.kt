package org.pet.project.rickandmorty.feature.character.impl.presentation.intent

import org.pet.project.rickandmorty.common.presentation.Intent

sealed interface CharacterSearchIntent : Intent {
    object Clear : CharacterSearchIntent
    class OpenCharacter(val characterId: Int) : CharacterSearchIntent
    class Search(val name: String) : CharacterSearchIntent
    object Refresh : CharacterSearchIntent
}