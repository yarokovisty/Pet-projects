package org.pet.project.rickandmorty.app.navigation.nested

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import org.pet.project.rickandmorty.core.navigation.navigator.LocalNestedNavigator
import org.pet.project.rickandmorty.feature.character.api.navigation.CharacterTab
import org.pet.project.rickandmorty.feature.character.impl.navigation.characterGraph
import org.pet.project.rickandmorty.feature.character.impl.navigation.characterSearchGraph
import org.pet.project.rickandmorty.feature.episode.impl.navigation.episodeGraph

@Composable
fun NestedNavGraph(modifier: Modifier = Modifier) {
    NestedLocalProvider {
        NavHost(
            navController = LocalNestedNavigator.current.navController,
            startDestination = CharacterTab,
            modifier = modifier
        ) {
            characterGraph()
            episodeGraph()
            characterSearchGraph()
        }
    }
}