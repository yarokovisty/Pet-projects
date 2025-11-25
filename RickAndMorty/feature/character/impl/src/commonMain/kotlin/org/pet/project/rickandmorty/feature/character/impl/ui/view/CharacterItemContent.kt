package org.pet.project.rickandmorty.feature.character.impl.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.pet.project.rickandmorty.design.component.AppSpacer
import org.pet.project.rickandmorty.design.component.AppTitleToolbar
import org.pet.project.rickandmorty.design.component.AppToolbarNavBackIcon
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Character
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Gender
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Status
import org.pet.project.rickandmorty.feature.character.impl.presentation.intent.CharacterItemIntent
import rickandmorty.design.resources.generated.resources.ic_arrow_forward
import rickandmorty.feature.character.impl.generated.resources.Res
import rickandmorty.feature.character.impl.generated.resources.character_gender_title
import rickandmorty.feature.character.impl.generated.resources.character_location_title
import rickandmorty.feature.character.impl.generated.resources.character_origin_title
import rickandmorty.feature.character.impl.generated.resources.character_species_title
import rickandmorty.feature.character.impl.generated.resources.character_view_all_episodes
import rickandmorty.design.resources.generated.resources.Res as R

@Composable
internal fun CharacterItemContent(
    character: Character,
    onIntent: (CharacterItemIntent) -> Unit,
) {
    Column {
        Toolbar(
            characterName = character.name,
            characterGender = character.gender,
            onBack = { onIntent(CharacterItemIntent.Back) }
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            item {
                CharacterImage(character.image)

                AppSpacer(height = 16.dp)
            }

            item {
                CharacterStatus(character.status)

                AppSpacer(height = 8.dp)
            }

            item {
                CharacterInfo(
                    title = stringResource(Res.string.character_species_title),
                    content = character.species
                )
            }

            item {
                CharacterInfo(
                    title = stringResource(Res.string.character_gender_title),
                    content = stringResource(character.gender.value)
                )
            }

            // TODO обработать случай unknown
            item {
                CharacterInfo(
                    title = stringResource(Res.string.character_origin_title),
                    content = character.origin,
                    onClick = {
                        onIntent(CharacterItemIntent.OpenOriginScreen(character.origin))
                    }
                )
            }

            item {
                CharacterInfo(
                    title = stringResource(Res.string.character_location_title),
                    content = character.location,
                    onClick = {
                        onIntent(CharacterItemIntent.OpenLocationScreen(character.location))
                    }
                )
            }

            item {
                AppSpacer(height = 20.dp)

                EpisodesButton(
                    onClick = { onIntent(CharacterItemIntent.OpenAllEpisodes) }
                )

                AppSpacer(height = 20.dp)
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Toolbar(
    characterName: String,
    characterGender: Gender,
    onBack: () -> Unit
) {
    TopAppBar(
        title = { AppTitleToolbar(characterName) },
        navigationIcon = { AppToolbarNavBackIcon(onClick = onBack) },
        actions = { GenderIcon(characterGender) }
    )
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
private fun CharacterStatus(status: Status) {
    Text(
        text = stringResource(status.value),
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(status.color)
            .padding(
                horizontal = 8.dp,
                vertical = 4.dp
            ),
        color = Color.White,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun CharacterInfo(
    title: String,
    content: String,
) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 12.sp
        )

        Text(content)
    }
}

@Composable
private fun CharacterInfo(
    title: String,
    content: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 12.sp
            )

            Text(content)
        }

        Icon(
            painter = painterResource(R.drawable.ic_arrow_forward),
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colorScheme.onBackground
        )
    }

}

@Composable
private fun EpisodesButton(
    onClick: () -> Unit,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(Res.string.character_view_all_episodes),
            modifier = Modifier.padding(vertical = 4.dp),
            fontSize = 16.sp
        )
    }
}