package org.pet.project.rickandmorty.app.navigation.global

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import org.pet.project.rickandmorty.core.navigation.navigator.LocalGlobalNavigator
import org.pet.project.rickandmorty.feature.episode.impl.navigation.CharacterEpisodeNavigator
import org.pet.project.rickandmorty.feature.episode.impl.navigation.LocalCharacterEpisodeNavigator
import org.pet.project.rickandmorty.feature.location.impl.navigation.LocalLocationNavigator
import org.pet.project.rickandmorty.feature.location.impl.navigation.LocationNavigator

@Composable
fun GlobalLocalProvider(content: @Composable () -> Unit) {
	val globalNavigator = LocalGlobalNavigator.current
	val providers = arrayOf(
		LocalLocationNavigator provides LocationNavigator(globalNavigator),
		LocalCharacterEpisodeNavigator provides CharacterEpisodeNavigator(globalNavigator),
	)

	CompositionLocalProvider(values = providers, content = content)
}