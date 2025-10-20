package org.pet.project.rickandmorty.feature.character.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import org.pet.project.rickandmorty.utils.shimmer
import org.pet.project.rickandmorty.feature.character.ui.res.characterCardHeight

@Composable
internal fun CharacterListSkeleton() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(characterCardHeight)
            .clip(ShapeDefaults.Medium)
            .shimmer()
    )
}