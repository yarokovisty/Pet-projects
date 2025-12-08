package org.pet.project.rickandmorty.feature.character.impl.presentation.intent

import org.pet.project.rickandmorty.common.presentation.Intent

sealed interface CharacterSearchIntent : Intent {
    class Search(val name: String) : CharacterSearchIntent
    object Clear : CharacterSearchIntent
    object Refresh : CharacterSearchIntent
}