package org.pet.project.rickandmorty.feature.episode.ui.view

import androidx.compose.foundation.border
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.stringResource
import org.pet.project.rickandmorty.design.component.AppSpacer
import org.pet.project.rickandmorty.feature.episode.domain.entity.Episode
import org.pet.project.rickandmorty.feature.episode.domain.entity.Season
import rickandmorty.feature.episode.generated.resources.Res
import rickandmorty.feature.episode.generated.resources.amount_episode
import rickandmorty.feature.episode.generated.resources.episode
import rickandmorty.feature.episode.generated.resources.num_season

@Composable
internal fun CharacterEpisodeContent(
    seasons: List<Season>,
    characterImage: String
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        item {
            SeasonsHorizontalScroll(seasons)
        }

        item {
            AppSpacer(height = 8.dp)
        }

        item {
            CharacterImage(characterImage)
        }

        item {
            AppSpacer(height = 24.dp)
        }

        seasons.forEach { season ->
            stickyHeader {
                SeasonHeader(season.num)
            }

            item {
                AppSpacer(height = 16.dp)
            }

            items(season.episodes, key = { it.id }) { episode ->
                EpisodeItem(episode)
            }
        }
    }
}

@Composable
private fun SeasonsHorizontalScroll(seasons: List<Season>) {
    println(seasons.size)
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(seasons, key = { it.num }) { season ->
            SeasonItem(season)
        }
    }
}

@Composable
private fun SeasonItem(season: Season) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = stringResource(Res.string.num_season, season.num),
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.labelMedium
        )

        AppSpacer(height = 2.dp)

        Text(
            text = stringResource(Res.string.amount_episode, season.amountEpisodes),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun CharacterImage(image: String) {
    AsyncImage(
        model = image,
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(12.dp))
    )
}

@Composable
private fun SeasonHeader(seasonNum: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = MaterialTheme.shapes.medium
            )
            .border(width = 1.dp, color = MaterialTheme.colorScheme.outline, shape = MaterialTheme.shapes.medium)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(Res.string.num_season, seasonNum),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun EpisodeItem(episode: Episode) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
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
    modifier: Modifier = Modifier
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