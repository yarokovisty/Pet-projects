package org.pet.project.rickandmorty.feature.location.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.pet.project.rickandmorty.design.component.AppSpacer
import rickandmorty.composeapp.generated.resources.Res
import rickandmorty.composeapp.generated.resources.location_amount_residents_title
import rickandmorty.composeapp.generated.resources.location_dimension_title
import rickandmorty.composeapp.generated.resources.location_type_title
import rickandmorty.design.resources.generated.resources.ic_dimension
import rickandmorty.design.resources.generated.resources.ic_group_people
import rickandmorty.design.resources.generated.resources.ic_location
import rickandmorty.design.resources.generated.resources.Res as R

@Composable
internal fun LocationItemInfoContent(
    name: String,
    type: String,
    dimension: String,
    amountResidents: Int
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Title(
            text = name,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        AppSpacer(height = 16.dp)

        InfoCards(
            type = type,
            dimension = dimension,
            amountResidents = amountResidents
        )
    }
}

@Composable
private fun Title(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.headlineSmall
    )
}

@Composable
private fun InfoCards(
    type: String,
    dimension: String,
    amountResidents: Int
) {
    val lazyListState = rememberLazyGridState()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 300.dp),
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 300.dp),
        state = lazyListState,
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            InfoCard(
                icon = painterResource(R.drawable.ic_location),
                title = stringResource(Res.string.location_type_title),
                value = type
            )
        }

        item {
            InfoCard(
                icon = painterResource(R.drawable.ic_dimension),
                title = stringResource(Res.string.location_dimension_title),
                value = dimension
            )
        }

        item {
            InfoCard(
                icon = painterResource(R.drawable.ic_group_people),
                title = stringResource(Res.string.location_amount_residents_title),
                value = amountResidents.toString()
            )
        }
    }
}

@Composable
private fun InfoCard(
    icon: Painter,
    title: String,
    value: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        shape = ShapeDefaults.Small
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = icon, contentDescription = null)

            AppSpacer(width = 12.dp)

            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                )

                AppSpacer(height = 4.dp)

                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}