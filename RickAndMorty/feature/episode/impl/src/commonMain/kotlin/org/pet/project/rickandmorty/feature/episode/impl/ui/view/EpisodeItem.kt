package org.pet.project.rickandmorty.feature.episode.impl.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.pet.project.rickandmorty.design.component.AppSpacer
import org.pet.project.rickandmorty.feature.episode.api.domain.entity.Episode
import rickandmorty.feature.episode.impl.generated.resources.Res
import rickandmorty.feature.episode.impl.generated.resources.episode

@Composable
internal fun EpisodeItem(
    episode: Episode
) {
    Row(Modifier.fillMaxWidth()) {
        SeriaItem(episode.seria)

        AppSpacer(width = 4.dp)

        EpisodeInfo(
            episodeName = episode.name,
            airDate = episode.airDate
        )
    }
}

@Composable
private fun RowScope.SeriaItem(seria: Int) {
    Column(modifier = Modifier.weight(1f)) {
        Text(
            text = stringResource(Res.string.episode),
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.labelMedium
        )

        AppSpacer(height = 2.dp)

        Text(
            text = "$seria",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun RowScope.EpisodeInfo(
    episodeName: String,
    airDate: String
) {
    Column(
        modifier = Modifier.weight(2f),
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = episodeName,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.End
        )

        AppSpacer(height = 2.dp)

        Text(
            text = airDate,
            style = MaterialTheme.typography.labelMedium,
            fontStyle = FontStyle.Italic
        )
    }
}