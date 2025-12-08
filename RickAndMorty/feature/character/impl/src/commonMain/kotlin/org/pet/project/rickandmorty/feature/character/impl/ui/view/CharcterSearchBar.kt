package org.pet.project.rickandmorty.feature.character.impl.ui.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.pet.project.rickandmorty.feature.character.impl.presentation.intent.CharacterSearchIntent
import org.pet.project.rickandmorty.feature.character.impl.presentation.state.SearchInputState
import rickandmorty.design.resources.generated.resources.Res
import rickandmorty.design.resources.generated.resources.ic_close
import rickandmorty.design.resources.generated.resources.ic_search
import rickandmorty.feature.character.impl.generated.resources.character_search_placeholder
import rickandmorty.feature.character.impl.generated.resources.Res as SearchRes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CharacterSearchBar(
    state: SearchInputState,
    onIntent: (CharacterSearchIntent) -> Unit,
) {
    OutlinedTextField(
        value = state.query,
        onValueChange = { name -> onIntent(CharacterSearchIntent.Search(name)) },
        placeholder = { SearchPlaceHolder() },
        leadingIcon = { LeadingIcon() },
        trailingIcon = {
            if (state.clearShow) {
                TrailingIcon(onClick = { onIntent(CharacterSearchIntent.Clear) })
            }
        },
        singleLine = true,
        shape = MaterialTheme.shapes.medium,
        colors = OutlinedTextFieldDefaults.colors().copy(
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
}

@Composable
private fun SearchPlaceHolder() {
    Text(stringResource(SearchRes.string.character_search_placeholder))
}

@Composable
private fun LeadingIcon() {
    Icon(
        painter = painterResource(Res.drawable.ic_search),
        contentDescription = null
    )
}

@Composable
private fun TrailingIcon(
    onClick: () -> Unit,
) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(Res.drawable.ic_close),
            contentDescription = null
        )
    }
}