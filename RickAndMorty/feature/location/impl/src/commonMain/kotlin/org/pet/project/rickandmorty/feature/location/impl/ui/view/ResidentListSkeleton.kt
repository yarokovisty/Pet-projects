package org.pet.project.rickandmorty.feature.location.impl.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.pet.project.rickandmorty.util.shimmer

@Composable
internal fun ResidentListSkeleton() {
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
            .height(300.dp)
            .clip(ShapeDefaults.Small)
            .shimmer()
    )
}
