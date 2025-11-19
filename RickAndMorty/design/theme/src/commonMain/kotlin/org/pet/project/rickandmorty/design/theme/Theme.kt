package org.pet.project.rickandmorty.design.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val lightRickMortyColors = lightColorScheme(
	primary = Color(0xFF00BFA5),
	onPrimary = Color.White,
	primaryContainer = Color(0xFFA7FFEB),
	onPrimaryContainer = Color(0xFF00201C),
	secondary = Color(0xFFC0CA33),
	onSecondary = Color(0xFF1B1C00),
	secondaryContainer = Color(0xFFF5F7B2),
	onSecondaryContainer = Color(0xFF131400),
	tertiary = Color(0xFFFF6F61),
	onTertiary = Color.White,
	background = Color(0xFFF0FDF9),
	onBackground = Color(0xFF00201C),
	surface = Color.White,
	onSurface = Color(0xFF001E19),
	error = Color(0xFFD32F2F),
	outline = Color(0xFF7BC4B2)
)

private val darkRickMortyColors = darkColorScheme(
	primary = Color(0xFF66FFF9),
	onPrimary = Color(0xFF00332E),
	primaryContainer = Color(0xFF005049),
	onPrimaryContainer = Color(0xFFA7FFEB),
	secondary = Color(0xFFE6EE9C),
	onSecondary = Color(0xFF303300),
	secondaryContainer = Color(0xFF474A00),
	onSecondaryContainer = Color(0xFFF5F7B2),
	tertiary = Color(0xFFFF8A80),
	onTertiary = Color(0xFF4A0005),
	background = Color(0xFF0B1211),
	onBackground = Color(0xFFE0FFF9),
	surface = Color(0xFF101817),
	onSurface = Color(0xFFDFFCF6),
	error = Color(0xFFCF6679),
	outline = Color(0xFF4B6E67)
)

val colorScheme: ColorScheme
	@Composable
	get() = if (isSystemInDarkTheme()) darkRickMortyColors else lightRickMortyColors

