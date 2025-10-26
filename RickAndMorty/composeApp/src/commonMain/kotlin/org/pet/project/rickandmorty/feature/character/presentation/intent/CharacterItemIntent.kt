package org.pet.project.rickandmorty.feature.character.presentation.intent

import org.pet.project.rickandmorty.common.presentation.Intent

sealed interface CharacterItemIntent : Intent {
    object Refresh : CharacterItemIntent
}