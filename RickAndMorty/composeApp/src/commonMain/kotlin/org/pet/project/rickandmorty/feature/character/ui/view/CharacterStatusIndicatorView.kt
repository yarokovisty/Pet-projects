package org.pet.project.rickandmorty.feature.character.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.pet.project.rickandmorty.feature.character.domain.entity.Status

@Composable
internal fun CharacterStatusIndicatorView(status: Status) {
    val color = when (status) {
        Status.ALIVE -> Color.Green
        Status.DEAD -> Color.Red
        Status.UNKNOWN -> Color.Yellow
    }

    Box(
        modifier = Modifier
            .size(8.dp)
            .clip(CircleShape)
            .background(color)
    )
}