package org.pet.project.rickandmorty.feature.episode.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.pet.project.rickandmorty.design.component.AppSpacer
import org.pet.project.rickandmorty.feature.episode.domain.entity.Episode
import rickandmorty.feature.episode.generated.resources.Res
import rickandmorty.feature.episode.generated.resources.episode

@Composable
internal fun EpisodeItem(
    episode: Episode
) {
    Box(Modifier.fillMaxWidth()) {
        SeriaItem(episode.seria)

        EpisodeInfo(
            episodeName = episode.name,
            airDate = episode.airDate,
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}

@Composable
private fun SeriaItem(seria: Int) {
    Column {
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
private fun EpisodeInfo(
    episodeName: String,
    airDate: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = episodeName,

            )

        AppSpacer(height = 2.dp)

        Text(
            text = airDate,
            style = MaterialTheme.typography.labelMedium,
            fontStyle = FontStyle.Italic
        )
    }
}