package org.pet.project.rickandmorty.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import org.pet.project.rickandmorty.core.navigation.navigator.LocalGlobalNavigator
import org.pet.project.rickandmorty.core.navigation.navigator.LocalNestedNavigator
import org.pet.project.rickandmorty.feature.character.api.navigation.CharacterTab
import org.pet.project.rickandmorty.feature.character.impl.navigation.CharacterItemNavigator
import org.pet.project.rickandmorty.feature.character.impl.navigation.CharacterListNavigator
import org.pet.project.rickandmorty.feature.character.impl.navigation.CharacterSearchNavigator
import org.pet.project.rickandmorty.feature.character.impl.navigation.LocalCharacterItemNavigator
import org.pet.project.rickandmorty.feature.character.impl.navigation.LocalCharacterListNavigator
import org.pet.project.rickandmorty.feature.character.impl.navigation.LocalCharacterSearchNavigator
import org.pet.project.rickandmorty.feature.character.impl.navigation.characterGraph
import org.pet.project.rickandmorty.feature.character.impl.navigation.characterSearchGraph
import org.pet.project.rickandmorty.feature.episode.impl.navigation.episodeGraph

@Composable
fun NestedNavGraph(modifier: Modifier = Modifier) {
    val globalNavController = LocalGlobalNavigator.current
    val nestedNavController = LocalNestedNavigator.current

    CompositionLocalProvider(
        LocalCharacterListNavigator provides CharacterListNavigator(nestedNavController),
        LocalCharacterItemNavigator provides CharacterItemNavigator(globalNavController, nestedNavController),
        LocalCharacterSearchNavigator provides CharacterSearchNavigator(nestedNavController)
    ) {
        NavHost(
            navController = nestedNavController.navController,
            startDestination = CharacterTab,
            modifier = modifier
        ) {
            characterGraph()
            episodeGraph()
            characterSearchGraph()
        }
    }
}