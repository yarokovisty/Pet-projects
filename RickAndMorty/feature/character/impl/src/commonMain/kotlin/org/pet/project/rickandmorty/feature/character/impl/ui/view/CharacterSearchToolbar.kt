package org.pet.project.rickandmorty.feature.character.impl.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.pet.project.rickandmorty.design.component.AppTitleToolbar
import org.pet.project.rickandmorty.feature.character.impl.presentation.intent.CharacterSearchIntent
import org.pet.project.rickandmorty.feature.character.impl.presentation.state.FilterMenuState
import rickandmorty.design.resources.generated.resources.Res
import rickandmorty.design.resources.generated.resources.ic_menu
import rickandmorty.feature.character.impl.generated.resources.character_search_title
import rickandmorty.feature.character.impl.generated.resources.Res as SearchRes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CharacterSearchToolbar(
    state: FilterMenuState,
    onIntent: (CharacterSearchIntent) -> Unit
) {
    TopAppBar(
        title = {
            AppTitleToolbar(stringResource(SearchRes.string.character_search_title))
        },
        actions = {
            Box {
                MenuIcon(onClick = { onIntent(CharacterSearchIntent.ToggleFilterMenu) })

                CharacterSearchFilterDropdownMenu(state = state, onIntent = onIntent)
            }
        }
    )
}

@Composable
private fun MenuIcon(onClick: () -> Unit) {
    IconButton(onClick) {
        Icon(painter = painterResource(Res.drawable.ic_menu), contentDescription = null)
    }
}