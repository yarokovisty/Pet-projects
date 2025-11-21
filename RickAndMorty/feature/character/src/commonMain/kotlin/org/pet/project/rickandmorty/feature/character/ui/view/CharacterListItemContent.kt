package org.pet.project.rickandmorty.feature.character.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.stringResource
import org.pet.project.rickandmorty.design.component.AppSpacer
import org.pet.project.rickandmorty.feature.character.domain.entity.Character
import org.pet.project.rickandmorty.feature.character.domain.entity.Gender
import org.pet.project.rickandmorty.feature.character.domain.entity.Status
import rickandmorty.feature.character.generated.resources.Res
import rickandmorty.feature.character.generated.resources.character_location_title

@Composable
internal fun CharacterListItemContent(
    character: Character,
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(150.dp),
        shape = ShapeDefaults.Medium
    ) {
        Row {
            CharacterImage(character.image)

            CharacterInfo(character)
        }
    }
}

@Composable
private fun CharacterImage(image: String) {
    AsyncImage(
        model = image,
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = Modifier.size(150.dp)
    )
}

@Composable
private fun RowScope.CharacterInfo(character: Character) {
    Column(
        modifier = Modifier
            .weight(1f)
            .padding(8.dp)
    ) {
        CharacterNameAndGender(
            name = character.name,
            gender = character.gender
        )

        AppSpacer(height = 8.dp)

        CharacterStatus(
            status = character.status,
            species = character.species
        )

        AppSpacer(height = 8.dp)

        CharacterAdditionalInfo(
            title = stringResource(Res.string.character_location_title),
            value = character.location
        )
    }
}

@Composable
private fun CharacterNameAndGender(
    name: String,
    gender: Gender,
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = name,
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 16.sp
        )
        GenderIcon(gender)
    }
}

@Composable
private fun CharacterStatus(status: Status, species: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        CharacterStatusIndicator(status.color)

        AppSpacer(width = 4.dp)

        val statusText = stringResource(status.value)
        val content = "$statusText - $species"
        Text(text = content, fontSize = 14.sp)
    }
}

@Composable
private fun CharacterStatusIndicator(color: Color) {
    Box(
        modifier = Modifier
            .size(8.dp)
            .clip(CircleShape)
            .background(color)
    )
}

@Composable
private fun CharacterAdditionalInfo(title: String, value: String) {
    Text(
        text = title,
        fontSize = 12.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Text(
        text = value,
        fontSize = 14.sp
    )
}
