package org.pet.project.rickandmorty.feature.character.impl.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.pet.project.rickandmorty.feature.character.api.domain.entity.Character
import org.pet.project.rickandmorty.feature.character.impl.presentation.state.CharacterListState

@Composable
internal fun CharacterListContent(
    lazyListState: LazyGridState,
    state: CharacterListState,
    onClickCharacter: (Character) -> Unit,
    modifier: Modifier = Modifier
) {
    val numItemSkeleton = 20

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 300.dp),
        modifier = modifier,
        state = lazyListState,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        userScrollEnabled = !state.skeleton

    ) {
        if (state.skeleton) {
            items(numItemSkeleton) {
                CharacterListItemSkeleton()
            }
        } else {
            items(
                items = state.characters,
                key = { character -> character.id }
            ) { character ->
                CharacterListItemContent(character) { onClickCharacter(character) }
            }
        }
    }
}