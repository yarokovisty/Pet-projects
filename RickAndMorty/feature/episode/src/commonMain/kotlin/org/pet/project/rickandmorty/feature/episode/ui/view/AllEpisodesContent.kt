package org.pet.project.rickandmorty.feature.episode.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.pet.project.rickandmorty.design.component.AppSpacer
import org.pet.project.rickandmorty.feature.episode.presentation.state.AllEpisodesState
import rickandmorty.feature.episode.generated.resources.Res
import rickandmorty.feature.episode.generated.resources.num_season

@Composable
internal fun AllEpisodesContent(
    lazyListState: LazyGridState,
    state: AllEpisodesState
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 300.dp),
        state = lazyListState,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        state.episodes.forEach { (season, episodes) ->
            stickyHeader {
                SeasonHeader(season)
            }

            items(episodes, key = { it.id }) { episode ->
                EpisodeItem(episode)
            }
        }

        if (state.uploading) {
            item {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
private fun SeasonHeader(num: Int) {
    Column(Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(Res.string.num_season, num),
            style = MaterialTheme.typography.titleLarge
        )

        AppSpacer(height = 8.dp)

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 2.dp
        )
    }
}