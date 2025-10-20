package org.pet.project.rickandmorty.feature.character.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.stringResource
import org.pet.project.rickandmorty.design.Spacing
import org.pet.project.rickandmorty.feature.character.domain.entity.Character
import org.pet.project.rickandmorty.feature.character.domain.entity.Status
import org.pet.project.rickandmorty.feature.character.ui.res.characterCardHeight
import org.pet.project.rickandmorty.design.component.AppSpacer
import rickandmorty.composeapp.generated.resources.Res
import rickandmorty.composeapp.generated.resources.character_location_title

@Composable
internal fun CharacterItemView(
    character: Character,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(characterCardHeight),
        shape = ShapeDefaults.Medium
    ) {
        Row {
            CharacterItemImageView(character.image)
            CharacterItemInfoView(character)
        }
    }
}

@Composable
private fun CharacterItemImageView(image: String) {
    AsyncImage(
        model = image,
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = Modifier.size(characterCardHeight)
    )
}

@Composable
private fun RowScope.CharacterItemInfoView(character: Character) {
    Column(
        modifier = Modifier
            .weight(1f)
            .padding(Spacing.sp2)
    ) {
        CharacterItemNameView(character.name)
        CharacterItemStatusView(character.status, character.species)
        AppSpacer(height = Spacing.sp2)
        CharacterItemAdditionalInfoView(
            stringResource(Res.string.character_location_title),
            character.location
        )
    }
}

@Composable
private fun CharacterItemNameView(name: String) {
    Text(
        text = name,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 16.sp
    )
}

@Composable
private fun CharacterItemStatusView(status: Status, species: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        CharacterStatusIndicatorView(status)

        AppSpacer(width = Spacing.sp1)

        val statusText = stringResource(status.value)
        val content = "$statusText - $species"

        Text(text = content, fontSize = 14.sp)
    }
}

@Composable
private fun CharacterItemAdditionalInfoView(title: String, value: String) {
    Text(text = title, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    Text(text = value, fontSize = 14.sp)
}
