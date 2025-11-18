package org.pet.project.rickandmorty.feature.character.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.pet.project.rickandmorty.util.shimmer

@Composable
internal fun CharacterListItemSkeleton() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(ShapeDefaults.Medium)
            .shimmer()
    )
}