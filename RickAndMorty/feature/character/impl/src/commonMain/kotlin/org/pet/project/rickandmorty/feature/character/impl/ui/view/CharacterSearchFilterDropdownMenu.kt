package org.pet.project.rickandmorty.feature.character.impl.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import org.pet.project.rickandmorty.feature.character.impl.presentation.intent.CharacterSearchIntent
import org.pet.project.rickandmorty.feature.character.impl.presentation.state.FilterMenuState

@Composable
internal fun CharacterSearchFilterDropdownMenu(
    state: FilterMenuState,
    onIntent: (CharacterSearchIntent) -> Unit,
) {
    val anchorOffset = remember { mutableStateOf(IntOffset.Zero) }
    val anchorHeight = remember { mutableStateOf(0) }

    if (state.expanded) {
        Box(
            modifier = Modifier.onGloballyPositioned { coords ->
                val pos = coords.localToRoot(Offset.Zero)
                anchorOffset.value = IntOffset(pos.x.toInt(), pos.y.toInt())
                anchorHeight.value = coords.size.height
            }
        ) {
            Popup(
                alignment = Alignment.TopStart,
                offset = IntOffset(
                    x = anchorOffset.value.x,
                    y = anchorOffset.value.y + anchorHeight.value
                ),
                onDismissRequest = { onIntent(CharacterSearchIntent.ToggleFilterMenu) },
                properties = PopupProperties(focusable = true)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    CharacterFilterListContent(
                        filters = state.filters,
                        onClickFilterToggle = {}
                    )
                }
            }
        }
    }
}

