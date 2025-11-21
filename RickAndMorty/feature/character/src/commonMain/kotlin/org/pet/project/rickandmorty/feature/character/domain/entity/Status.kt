package org.pet.project.rickandmorty.feature.character.domain.entity

import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.StringResource
import rickandmorty.feature.character.generated.resources.Res
import rickandmorty.feature.character.generated.resources.character_status_alive
import rickandmorty.feature.character.generated.resources.character_status_died
import rickandmorty.feature.character.generated.resources.character_status_unknown


enum class Status(
    val value: StringResource,
    val color: Color
) {
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
