package org.pet.project.rickandmorty.feature.character.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import org.koin.compose.viewmodel.koinViewModel
import org.pet.project.rickandmorty.common.utils.collectAsEffect
import org.pet.project.rickandmorty.design.Spacing
import org.pet.project.rickandmorty.feature.character.presentation.event.CharacterListEvent
import org.pet.project.rickandmorty.feature.character.presentation.intent.CharacterListIntent
import org.pet.project.rickandmorty.feature.character.presentation.state.CharacterListState
import org.pet.project.rickandmorty.feature.character.presentation.viewmodel.CharacterListViewModel
import org.pet.project.rickandmorty.feature.character.ui.view.CharacterItemView
import org.pet.project.rickandmorty.feature.character.ui.view.CharacterListSkeleton
import org.pet.project.rickandmorty.feature.character.ui.view.CharacterListToolbar
import org.pet.project.rickandmorty.feature.character.ui.view.CharacterListUploadView

@Composable
fun CharacterListScreen() {
    val viewModel = koinViewModel<CharacterListViewModel>()
    val state by viewModel.state.collectAsState()

    CharacterListScreen(
        state = state,
        event = viewModel.event
    ) { viewModel.onIntent(it) }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CharacterListScreen(
    state: CharacterListState,
    event: Flow<CharacterListEvent>,
    onIntent: (CharacterListIntent) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val lazyListState = rememberLazyGridState()
    val numItemSkeleton = 20

    LaunchedEffect(state.characters) {
        snapshotFlow {
            lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
        }
            .distinctUntilChanged()
            .collect { lastVisibleIndex ->
                if (lastVisibleIndex == state.characters.lastIndex) {
                    onIntent(CharacterListIntent.UploadCharacterList)
                }
            }
    }

    Column(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        CharacterListToolbar(scrollBehavior)

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 300.dp),
            modifier = Modifier.weight(1f),
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
                    CharacterItemView(character) {
                        onIntent(CharacterListIntent.OpenCharacterScreen(character))
                    }
                }
            }
        }

        if (state.isLoadingMore) {
            CharacterListUploadView()
        }
    }



    event.collectAsEffect {
        // TODO обработка ошибок при пагинации
    }
}