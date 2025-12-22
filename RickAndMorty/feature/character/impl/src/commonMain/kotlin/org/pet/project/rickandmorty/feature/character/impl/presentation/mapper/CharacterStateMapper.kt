package org.pet.project.rickandmorty.feature.character.impl.presentation.mapper

import org.jetbrains.compose.resources.getString
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Filter
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Gender
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Status

internal suspend fun Gender.toFilter(count: Int): Filter =
    Filter(
        amount = count,
        name = getString(value),
        selected = false,
        filter = this
    )

internal suspend fun Status.toFilter(count: Int): Filter =
    Filter(
        amount = count,
        name = getString(value),
        selected = false,
        filter = this
    )