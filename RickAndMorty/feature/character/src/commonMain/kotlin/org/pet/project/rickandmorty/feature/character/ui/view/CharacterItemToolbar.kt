package org.pet.project.rickandmorty.feature.character.ui.view

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import org.pet.project.rickandmorty.design.component.AppToolbarNavBackIcon
import org.pet.project.rickandmorty.feature.character.domain.entity.Gender

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CharacterItemToolbar(
    characterName: String,
    characterGender: Gender,
    onBack: () -> Unit
) {
    TopAppBar(
        title = { TitleToolbar(characterName) },
        navigationIcon = { AppToolbarNavBackIcon(onClick = onBack) },
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