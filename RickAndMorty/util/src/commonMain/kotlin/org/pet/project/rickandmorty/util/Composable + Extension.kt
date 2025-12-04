package org.pet.project.rickandmorty.util

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.distinctUntilChanged

@Suppress("ComposableNaming")
@Composable
fun LazyGridState.onReachEnd(
    action: () -> Unit
) {
    LaunchedEffect(Unit) {
        snapshotFlow {
            layoutInfo.visibleItemsInfo.lastOrNull()?.index
        }
            .distinctUntilChanged()
            .collect { lastVisibleIndex ->
                if (isScrolledToTheEnd()) action()
            }
    }
}

fun LazyGridState.isScrolledToTheEnd() = layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1