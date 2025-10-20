package org.pet.project.rickandmorty.feature.character.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.pet.project.rickandmorty.design.Spacing
import org.pet.project.rickandmorty.feature.character.domain.entity.Character
import org.pet.project.rickandmorty.feature.character.presentation.state.CharacterListState

@Composable
internal fun CharacterListView(
    modifier: Modifier,
    lazyListState: LazyGridState,
    state: CharacterListState,
    onClickCharacter: (Character) -> Unit
) {
    val numItemSkeleton = 20

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 300.dp),
        modifier = modifier,
        state = lazyListState,
        contentPadding = PaddingValues(horizontal = Spacing.sp4, vertical = Spacing.sp2),
        verticalArrangement = Arrangement.spacedBy(Spacing.sp4),
        horizontalArrangement = Arrangement.spacedBy(Spacing.sp4)

    ) {
        if (state.skeleton) {
            items(numItemSkeleton) {
                CharacterListSkeleton()
            }
        } else {
            items(
                items = state.characters,
                key = { character -> character.id }
            ) { character ->
                CharacterItemView(character) { onClickCharacter(character) }
            }
        }
    }
}