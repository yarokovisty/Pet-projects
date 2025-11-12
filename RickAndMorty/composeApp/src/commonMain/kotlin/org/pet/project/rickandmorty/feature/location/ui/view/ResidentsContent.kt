package org.pet.project.rickandmorty.feature.location.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.pet.project.rickandmorty.feature.location.presentation.intent.LocationItemIntent
import org.pet.project.rickandmorty.feature.location.presentation.state.ResidentListState
import rickandmorty.composeapp.generated.resources.Res
import rickandmorty.composeapp.generated.resources.location_residents_subtitle

internal fun LazyListScope.ResidentsContent(
	state: ResidentListState,
	onIntent: (LocationItemIntent) -> Unit
) {
	item {
		Title(
			modifier = Modifier
				.padding(
					start = 16.dp,
					top = 16.dp,
					end = 16.dp
				)
				.fillMaxWidth()
				.clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
				.background(MaterialTheme.colorScheme.primaryContainer)
				.padding(8.dp)
		)
	}

	items(state.visibleResidents.size) { index ->
		val resident = state.visibleResidents[index]
		val showDivider = state.visibleResidents.lastIndex != index

		ResidentItemView(
			icon = resident.image,
			name = resident.name,
			showDivider
		)
	}

	item {
		Box(
			modifier = Modifier
				.padding(horizontal = 16.dp)
				.fillMaxWidth()
				.clip(RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp))
				.background(MaterialTheme.colorScheme.primaryContainer)
		) {
			when {
				state.uploading -> {}
				!state.uploadAll -> UploadButton(
					onClick = { onIntent(LocationItemIntent.UploadResidents) }
				)
			}
		}
	}
}

@Composable
private fun Title(
	modifier: Modifier = Modifier
) {
	Text(
		text = stringResource(Res.string.location_residents_subtitle),
		modifier = modifier,
		style = MaterialTheme.typography.titleMedium,
		color = MaterialTheme.colorScheme.onPrimaryContainer
	)
}

@Composable
private fun UploadButton(onClick: () -> Unit) {
	Box(
		modifier =  Modifier
			.fillMaxWidth()
			.height(42.dp)
			.clickable(onClick = {  }),
		contentAlignment = Alignment.Center
	) {
		Text(
			text = "More",
			style = MaterialTheme.typography.labelLarge,
		)
	}

}
