package org.pet.project.rickandmorty.feature.character.impl.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import org.pet.project.rickandmorty.design.component.AppSpacer
import org.pet.project.rickandmorty.feature.character.impl.presentation.intent.CharacterSearchIntent
import org.pet.project.rickandmorty.feature.character.impl.presentation.state.FilterMenuState
import org.pet.project.rickandmorty.feature.character.impl.presentation.state.FilterState

@Composable
internal fun CharacterSearchFilterDropdownMenu(
    state: FilterMenuState,
    onIntent: (CharacterSearchIntent) -> Unit,
) {
    val anchorOffset = remember { mutableStateOf(IntOffset.Zero) }
    val anchorHeight = remember { mutableStateOf(0) }

    if (state.expanded) {
        Box(
            modifier = Modifier.onGloballyPositioned { coords ->
                val pos = coords.localToRoot(Offset.Zero)
                anchorOffset.value = IntOffset(pos.x.toInt(), pos.y.toInt())
                anchorHeight.value = coords.size.height
            }
        ) {
            Popup(
                alignment = Alignment.TopStart,
                offset = IntOffset(
                    x = anchorOffset.value.x,
                    y = anchorOffset.value.y + anchorHeight.value
                ),
                onDismissRequest = { onIntent(CharacterSearchIntent.ToggleFilterMenu) },
                properties = PopupProperties(

                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 96.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        state.filters.forEach { (title, filters) ->

                            item(span = { GridItemSpan(maxLineSpan) }) {
                                Header(title)
                            }

                            items(filters, key = { it.filter }) { filter ->
                                FilterToggle(
                                    filter = filter,
                                    onClick = {}
                                )
                            }

                            item {
                                AppSpacer(height = 4.dp)
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun Header(title: String) {
    Text(text = title, style = MaterialTheme.typography.titleSmall)
}

@Composable
private fun FilterToggle(
    filter: FilterState,
    onClick: () -> Unit,
) {
    val color = if (filter.selected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
    }

    Row(
        modifier = Modifier
            .background(color = color, shape = MaterialTheme.shapes.medium)
    ) {
        Text(text = filter.amount.toString(), color = MaterialTheme.colorScheme.onPrimary)

        AppSpacer(width = 4.dp)

        Text(
            text = filter.name,

        )
    }
}