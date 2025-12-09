package org.pet.project.rickandmorty.feature.character.api.domain.entity

import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.StringResource
import rickandmorty.feature.character.api.generated.resources.Res
import rickandmorty.feature.character.api.generated.resources.character_status_alive
import rickandmorty.feature.character.api.generated.resources.character_status_died
import rickandmorty.feature.character.api.generated.resources.character_status_unknown


enum class Status(
    override val value: StringResource,
    val color: Color
) : CharacterFilter {
    ALIVE(
        value = Res.string.character_status_alive,
        color = Color.Green
    ),
    DEAD(
        value = Res.string.character_status_died,
        color = Color.Red
    ),
    UNKNOWN(
        value = Res.string.character_status_unknown,
        color = Color.Yellow
    );
}
