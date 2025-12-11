package org.pet.project.rickandmorty.feature.character.impl.presentation.mapper

import org.jetbrains.compose.resources.getString
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Gender
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Status
import org.pet.project.rickandmorty.feature.character.impl.presentation.state.FilterState

internal suspend fun Gender.toFilterState(count: Int): FilterState =
    FilterState(
        amount = count,
        name = getString(value),
        selected = false,
        filter = this
    )

internal suspend fun Status.toFilterState(count: Int): FilterState =
    FilterState(
        amount = count,
        name = getString(value),
        selected = false,
        filter = this
    )