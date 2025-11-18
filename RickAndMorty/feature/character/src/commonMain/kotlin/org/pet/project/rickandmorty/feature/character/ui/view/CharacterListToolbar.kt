package org.pet.project.rickandmorty.feature.character.ui.view

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.stringResource
import rickandmorty.feature.character.generated.resources.Res
import rickandmorty.feature.character.generated.resources.character_list_toolbar_title

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CharacterListToolbar(
    scrollBehavior: TopAppBarScrollBehavior
) {
    TopAppBar(
        title = { TitleToolbar() },
        scrollBehavior = scrollBehavior
    )
}

@Composable
private fun TitleToolbar() {
    Text(
        stringResource(Res.string.character_list_toolbar_title),
        fontWeight = FontWeight.Medium
    )
}