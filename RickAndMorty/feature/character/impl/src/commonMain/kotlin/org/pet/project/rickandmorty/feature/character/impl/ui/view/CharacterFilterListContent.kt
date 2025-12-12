package org.pet.project.rickandmorty.feature.character.impl.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.pet.project.rickandmorty.design.component.AppSpacer
import org.pet.project.rickandmorty.feature.character.impl.presentation.state.FilterState

@Composable
internal fun CharacterFilterListContent(
	filters: Map<String, List<FilterState>>,
	onClickFilterToggle: () -> Unit
) {
	LazyVerticalGrid(
		columns = GridCells.Adaptive(minSize = 96.dp),
		modifier = Modifier
			.fillMaxWidth()
			.padding(horizontal = 16.dp, vertical = 8.dp),
		horizontalArrangement = Arrangement.spacedBy(8.dp),
		verticalArrangement = Arrangement.spacedBy(8.dp)
	) {
		filters.forEach { (title, filters) ->
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

@Composable
private fun Header(title: String) {
	Text(text = title, style = MaterialTheme.typography.titleSmall)
}

@Composable
private fun FilterToggle(
	filter: FilterState,
	onClick: () -> Unit,
) {
	val color = if (filter.selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.inverseSurface

	Row(
        modifier = Modifier
            .background(color = color, shape = MaterialTheme.shapes.small)
            .border(width = 2.dp, color = color, shape = MaterialTheme.shapes.small)
	) {
		Text(
            text = filter.amount.toString(),
            color =  if (filter.selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.surface,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .sizeIn(minWidth = 28.dp)
                .padding(4.dp)
        )

		Text(
            text = filter.name,
            color = color,
            modifier = Modifier
                .weight(1f)
                .background(MaterialTheme.colorScheme.surface)
                .padding(4.dp)
        )
	}
}