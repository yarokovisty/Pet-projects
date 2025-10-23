package org.pet.project.rickandmorty.feature.character.presentation.viewmodel

import org.pet.project.rickandmorty.common.presentation.BaseViewModel
import org.pet.project.rickandmorty.feature.character.presentation.intent.CharacterItemIntent
import org.pet.project.rickandmorty.feature.character.presentation.state.CharacterItemState

internal class CharacterItemViewModel(id: Int) : BaseViewModel<CharacterItemState, CharacterItemIntent, Nothing>() {

    override fun initState(): CharacterItemState = CharacterItemState(loading = true)

    override fun onIntent(intent: CharacterItemIntent) {
        TODO("Not yet implemented")
    }
}