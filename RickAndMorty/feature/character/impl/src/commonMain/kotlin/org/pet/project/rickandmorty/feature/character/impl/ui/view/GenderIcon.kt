package org.pet.project.rickandmorty.feature.character.impl.ui.view

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.painterResource
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Gender

@Composable
internal fun GenderIcon(gender: Gender) {
    val (icon, tint) = gender.icon

    Icon(
        painter = painterResource(icon),
        contentDescription = null,
        tint = tint
    )
}
