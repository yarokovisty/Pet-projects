package org.pet.project.rickandmorty.feature.character.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.stringResource
import org.pet.project.rickandmorty.design.component.AppSpacer
import org.pet.project.rickandmorty.feature.character.domain.entity.Character
import org.pet.project.rickandmorty.feature.character.domain.entity.Status

@Composable
internal fun CharacterItemContentView(
    character: Character,
    onBack: () -> Unit
) {
    Column {
        CharacterItemToolbar(
            characterName = character.name,
            characterGender = character.gender,
            onBack = onBack
        )

        val contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 8.dp
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = contentPadding
        ) {
            item {
                CharacterImage(character.image)

                AppSpacer(height = 16.dp)
            }

            item {
                CharacterStatusView(character.status)
            }
        }

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
private fun CharacterStatusView(status: Status) {
    val background = when (status) {
        Status.ALIVE -> Color.Green
        Status.DEAD -> Color.Red
        Status.UNKNOWN -> Color.Yellow
    }

    Text(
        text = stringResource(status.value),
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(background)
            .padding(
                horizontal = 8.dp,
                vertical = 4.dp
            ),
        color = Color.White,
        textAlign = TextAlign.Center
    )
}



