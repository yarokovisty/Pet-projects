package org.pet.project.rickandmorty.feature.location.impl.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.pet.project.rickandmorty.design.component.AppSpacer
import org.pet.project.rickandmorty.util.shimmer

@Composable
internal fun LocationItemInfoSkeleton() {
    Column(modifier = Modifier.fillMaxWidth()) {
        AppSpacer(height = 8.dp)

        TitleSkeleton(Modifier.align(Alignment.CenterHorizontally))

        AppSpacer(height = 16.dp)

        InfoCardsSkeleton()
    }

}

@Composable
private fun TitleSkeleton(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(200.dp)
            .height(32.dp)
            .clip(ShapeDefaults.Small)
            .shimmer()
    )
}

@Composable
private fun InfoCardsSkeleton() {
    val lazyListState = rememberLazyGridState()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 300.dp),
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 300.dp),
        state = lazyListState,
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(3) {
            InfoCardSkeleton()
        }
    }
}

@Composable
private fun InfoCardSkeleton() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clip(ShapeDefaults.Small)
            .shimmer()
    )
}
