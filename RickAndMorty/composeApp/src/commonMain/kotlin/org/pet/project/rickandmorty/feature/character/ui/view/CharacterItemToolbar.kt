package org.pet.project.rickandmorty.feature.character.ui.view

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.pet.project.rickandmorty.feature.character.domain.entity.Gender
import rickandmorty.composeapp.generated.resources.Res
import rickandmorty.composeapp.generated.resources.ic_arrow_back
import rickandmorty.composeapp.generated.resources.ic_gender_female
import rickandmorty.composeapp.generated.resources.ic_gender_male
import rickandmorty.composeapp.generated.resources.ic_gender_unknown

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CharacterItemToolbar(
    characterName: String,
    characterGender: Gender,
    onBack: () -> Unit
) {
    TopAppBar(
        title = { TitleToolbar(characterName) },
        navigationIcon = { NavigationIcon(onClick = onBack) },
        actions = { GenderIcon(characterGender) }
    )
}

@Composable
private fun TitleToolbar(title: String) {
    Text(
        text = title,
        fontWeight = FontWeight.Medium
    )
}

@Composable
private fun NavigationIcon(onClick: () -> Unit) {
    IconButton(
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_arrow_back),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}