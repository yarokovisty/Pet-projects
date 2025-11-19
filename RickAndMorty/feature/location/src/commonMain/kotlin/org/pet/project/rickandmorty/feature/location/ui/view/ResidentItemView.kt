package org.pet.project.rickandmorty.feature.location.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.pet.project.rickandmorty.design.component.AppSpacer

@Composable
internal fun ResidentItemView(
	icon: String,
	name: String,
	showDivider: Boolean
) {
	Column(
		modifier = Modifier
			.padding(horizontal = 16.dp)
			.fillMaxWidth()
			.background(MaterialTheme.colorScheme.primaryContainer)
			.padding(horizontal = 8.dp, vertical = 4.dp)
	) {
		Row(
			modifier = Modifier.fillMaxWidth(),
			verticalAlignment = Alignment.CenterVertically
		) {
			ResidentIcon(icon)

			AppSpacer(width = 8.dp)

			Text(
				text = name,
				modifier = Modifier.weight(1f),
				style = MaterialTheme.typography.bodyLarge,
				color = MaterialTheme.colorScheme.onPrimaryContainer
			)
		}

		if (showDivider) {
			AppSpacer(height = 4.dp)

			HorizontalDivider(
				modifier = Modifier.fillMaxWidth(),
				thickness = 1.dp,
				color = MaterialTheme.colorScheme.outline
			)
		}
	}

}

@Composable
private fun ResidentIcon(icon: String) {
	AsyncImage(
		model = icon,
		contentDescription = null,
		modifier = Modifier
			.size(52.dp)
			.clip(CircleShape)
			.border(2.dp, MaterialTheme.colorScheme.onPrimaryContainer, CircleShape),
		contentScale = ContentScale.Crop
	)
}