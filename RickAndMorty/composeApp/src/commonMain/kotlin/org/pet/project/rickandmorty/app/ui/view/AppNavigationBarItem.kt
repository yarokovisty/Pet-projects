package org.pet.project.rickandmorty.app.ui.view

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import rickandmorty.composeapp.generated.resources.Res
import rickandmorty.composeapp.generated.resources.ic_person
import rickandmorty.composeapp.generated.resources.ic_play_arrow
import rickandmorty.composeapp.generated.resources.ic_search
import rickandmorty.composeapp.generated.resources.nav_bar_item_characters
import rickandmorty.composeapp.generated.resources.nav_bar_item_episodes
import rickandmorty.composeapp.generated.resources.nav_bar_item_search

internal enum class AppNavigationBarItem(
    val icon: DrawableResource,
    val title: StringResource
) {
    CHARACTERS(
        Res.drawable.ic_person,
        Res.string.nav_bar_item_characters
    ),
    EPISODES(
        Res.drawable.ic_play_arrow,
        Res.string.nav_bar_item_episodes
    ),
    SEARCH(
        Res.drawable.ic_search,
        Res.string.nav_bar_item_search
    );
}