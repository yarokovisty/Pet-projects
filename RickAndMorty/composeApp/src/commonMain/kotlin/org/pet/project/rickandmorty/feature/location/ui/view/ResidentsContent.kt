package org.pet.project.rickandmorty.feature.location.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.pet.project.rickandmorty.design.component.AppSpacer
import org.pet.project.rickandmorty.feature.location.presentation.state.ResidentListState
import rickandmorty.composeapp.generated.resources.Res
import rickandmorty.composeapp.generated.resources.location_residents_subtitle

@Composable
internal fun ResidentsContent(state: ResidentListState) {
    val lazyListState = rememberLazyGridState()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 300.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(8.dp)
            ),
        state = lazyListState,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        userScrollEnabled = false
    ) {
        item {
            Title()
            AppSpacer(height = 12.dp)
        }

        items(state.visibleResidents, key = { it.id }) { resident ->
            ResidentItemView(
                icon = resident.image,
                name = resident.name
            )
        }
    }
}

@Composable
private fun Title() {
    Text(
        text = stringResource(Res.string.location_residents_subtitle),
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
private fun ResidentsView(state: ResidentListState) {

}

@Composable
private fun ResidentItemView(
    icon: String,
    name: String
) {
    Row {
        Text(name)
    }
}