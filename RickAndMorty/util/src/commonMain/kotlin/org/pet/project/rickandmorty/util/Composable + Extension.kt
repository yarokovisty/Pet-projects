package org.pet.project.rickandmorty.util

import androidx.compose.foundation.lazy.grid.LazyGridState

fun LazyGridState.isScrolledToTheEnd() = layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
