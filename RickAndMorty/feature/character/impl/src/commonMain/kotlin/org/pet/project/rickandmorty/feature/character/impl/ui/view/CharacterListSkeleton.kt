package org.pet.project.rickandmorty.feature.character.impl.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.pet.project.rickandmorty.util.shimmer

@Composable
internal fun CharacterListSkeleton(modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 300.dp),
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        userScrollEnabled = false

    ) {
        items(20) {
            CharacterListItemSkeleton()
        }
    }
}

@Composable
private fun CharacterListItemSkeleton() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(ShapeDefaults.Medium)
            .shimmer()
    )
}