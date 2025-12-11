package org.pet.project.rickandmorty.feature.character.impl.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.pet.project.rickandmorty.design.component.AppErrorScreen
import org.pet.project.rickandmorty.feature.character.impl.presentation.intent.CharacterSearchIntent
import org.pet.project.rickandmorty.feature.character.impl.presentation.state.SearchContentState
import org.pet.project.rickandmorty.feature.character.impl.presentation.state.SearchResultState
import rickandmorty.feature.character.impl.generated.resources.Res
import rickandmorty.feature.character.impl.generated.resources.character_search_empty_result
import rickandmorty.feature.character.impl.generated.resources.character_search_initial
import rickandmorty.feature.character.impl.generated.resources.character_search_success_result

@Composable
internal fun CharacterSearchContent(
    state: SearchResultState,
    onIntent: (CharacterSearchIntent) -> Unit,
) {
    when {
        state.error -> AppErrorScreen(onClick = { onIntent(CharacterSearchIntent.Refresh) })
        state.loading -> CharacterSearchLoading()
        state.notFound -> CharacterSearchNotFound(state.query)
        state.content == null -> CharacterSearchInitial()
        else -> CharacterSearchSuccess(state = state.content, onIntent = onIntent)
    }
}

@Composable
private fun CharacterSearchLoading() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 28.dp)
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(36.dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
private fun CharacterSearchNotFound(name: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 28.dp)
    ) {
        Text(
            text = stringResource(
                Res.string.character_search_empty_result,
                name
            ),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun CharacterSearchInitial() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 28.dp)
    ) {
        Text(
            text = stringResource(Res.string.character_search_initial),
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun CharacterSearchSuccess(
    state: SearchContentState,
    onIntent: (CharacterSearchIntent) -> Unit,
) {
    Column {
        NumFoundCharactersText(amount = state.numFound, characterName = state.name)

        CharacterListContent(
            characters = state.filteredCharacters,
            onClickCharacter = { character ->
                onIntent(CharacterSearchIntent.OpenCharacter(character.id))
            }
        )
    }
}

@Composable
private fun NumFoundCharactersText(
    amount: Int,
    characterName: String,
) {
    Text(
        text = stringResource(
            Res.string.character_search_success_result,
            amount, characterName
        ),
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}