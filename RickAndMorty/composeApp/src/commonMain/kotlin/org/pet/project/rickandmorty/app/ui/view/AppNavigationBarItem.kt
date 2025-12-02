package org.pet.project.rickandmorty.app.ui.view

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.pet.project.rickandmorty.core.navigation.Tab
import org.pet.project.rickandmorty.feature.character.impl.navigation.CharacterTab
import org.pet.project.rickandmorty.feature.episode.navigation.EpisodeTab
import rickandmorty.composeapp.generated.resources.Res
import rickandmorty.composeapp.generated.resources.nav_bar_item_characters
import rickandmorty.composeapp.generated.resources.nav_bar_item_episodes
import rickandmorty.composeapp.generated.resources.nav_bar_item_search
import rickandmorty.design.resources.generated.resources.ic_person
import rickandmorty.design.resources.generated.resources.ic_play_arrow
import rickandmorty.design.resources.generated.resources.ic_search
import rickandmorty.design.resources.generated.resources.Res as R

internal enum class AppNavigationBarItem(
    val icon: DrawableResource,
    val title: StringResource,
    val tab: Tab
) {
    CHARACTERS(
        R.drawable.ic_person,
        Res.string.nav_bar_item_characters,
        CharacterTab
    ),
    EPISODES(
        R.drawable.ic_play_arrow,
        Res.string.nav_bar_item_episodes,
        EpisodeTab
    ),
    SEARCH(
        R.drawable.ic_search,
        Res.string.nav_bar_item_search,
        SearchTab
    );
}

object SearchTab : Tab