package org.pet.project.rickandmorty.feature.location.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.pet.project.rickandmorty.feature.location.presentation.intent.LocationItemIntent
import org.pet.project.rickandmorty.feature.location.presentation.state.ResidentListState
import rickandmorty.feature.location.generated.resources.Res
import rickandmorty.feature.location.generated.resources.location_residents_subtitle
import rickandmorty.feature.location.generated.resources.location_residents_upload_more

internal fun LazyListScope.ResidentsContent(
	state: ResidentListState,
	onIntent: (LocationItemIntent) -> Unit
) {
	if (!state.loadError) {
		item {
			Title(modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp))
		}

		items(
			items = state.visibleResidents,
			key = { it.id }
		) { resident ->

			ResidentItemView(
				icon = resident.image,
				name = resident.name,
				modifier = Modifier.animateItem()
			)
		}

		item {
			if (state.uploading) {
				UploadIndicator()
			}
		}

		item {
			if (state.visibleMore) {
				UploadButton(
					onClick = { onIntent(LocationItemIntent.UploadResidents) },
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
		style = MaterialTheme.typography.titleMedium
	)
}

@Composable
private fun UploadIndicator() {
	Box(
		modifier = Modifier.fillMaxWidth(),
		contentAlignment = Alignment.Center
	) {
		CircularProgressIndicator(Modifier.size(20.dp))
	}
}

@Composable
private fun UploadButton(
	onClick: () -> Unit,
	modifier: Modifier = Modifier
) {
	Button(
		modifier = modifier
			.padding(horizontal = 16.dp)
			.fillMaxWidth(),
		onClick = onClick
	) {
		Text(
			text = stringResource(Res.string.location_residents_upload_more),
			style = MaterialTheme.typography.labelLarge,
			color = MaterialTheme.colorScheme.onPrimaryContainer
		)
	}
}
