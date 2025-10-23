package org.pet.project.rickandmorty.feature.character.presentation.state

import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.pet.project.rickandmorty.common.presentation.State
import rickandmorty.composeapp.generated.resources.Res
import rickandmorty.composeapp.generated.resources.character_status_unknown
import rickandmorty.composeapp.generated.resources.ic_gender_unknown

internal data class CharacterItemState(
    val loading: Boolean = false,
    val error: Boolean = false,
    val name: String = "",
    val gender: DrawableResource = Res.drawable.ic_gender_unknown,
    val image: String = "",
    val status: CharacterStatusState = CharacterStatusState(),
    val species: String = "",
    val origin: String = "",
) : State

internal data class CharacterStatusState(
    val color: Color = Color.Yellow,
    val text: StringResource = Res.string.character_status_unknown
)