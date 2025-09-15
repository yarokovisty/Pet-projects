package org.pet.project.rickandmorty.feature.character.domain.entity

import org.jetbrains.compose.resources.StringResource
import rickandmorty.composeapp.generated.resources.Res
import rickandmorty.composeapp.generated.resources.character_status_alive
import rickandmorty.composeapp.generated.resources.character_status_died
import rickandmorty.composeapp.generated.resources.character_status_unknown

enum class Status(val value: StringResource) {
    ALIVE(Res.string.character_status_alive),
    DEAD(Res.string.character_status_died),
    UNKNOWN(Res.string.character_status_unknown);
}
