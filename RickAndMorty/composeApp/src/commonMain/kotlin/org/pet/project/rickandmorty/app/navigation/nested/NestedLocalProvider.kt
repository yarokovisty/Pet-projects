package org.pet.project.rickandmorty.app.navigation.nested

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import org.pet.project.rickandmorty.core.navigation.navigator.LocalGlobalNavigator
import org.pet.project.rickandmorty.core.navigation.navigator.LocalNestedNavigator
import org.pet.project.rickandmorty.feature.character.impl.navigation.CharacterItemNavigator
import org.pet.project.rickandmorty.feature.character.impl.navigation.CharacterListNavigator
import org.pet.project.rickandmorty.feature.character.impl.navigation.CharacterSearchNavigator
import org.pet.project.rickandmorty.feature.character.impl.navigation.LocalCharacterItemNavigator
import org.pet.project.rickandmorty.feature.character.impl.navigation.LocalCharacterListNavigator
import org.pet.project.rickandmorty.feature.character.impl.navigation.LocalCharacterSearchNavigator

@Composable
fun NestedLocalProvider(content: @Composable () -> Unit) {
	val globalNavController = LocalGlobalNavigator.current
	val nestedNavController = LocalNestedNavigator.current
	val providers = arrayOf(
		LocalCharacterListNavigator provides CharacterListNavigator(nestedNavController),
		LocalCharacterItemNavigator provides CharacterItemNavigator(globalNavController, nestedNavController),
		LocalCharacterSearchNavigator provides CharacterSearchNavigator(nestedNavController),
	)

	CompositionLocalProvider(values = providers, content = content)
}
