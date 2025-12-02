package org.pet.project.rickandmorty.app.ui.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.Flow
import org.pet.project.rickandmorty.app.navigation.NestedNavGraph
import org.pet.project.rickandmorty.app.navigation.impl.rememberMainNavigator
import org.pet.project.rickandmorty.app.presentation.event.MainEvent
import org.pet.project.rickandmorty.app.presentation.intent.MainIntent
import org.pet.project.rickandmorty.app.presentation.viewmodel.MainViewModel
import org.pet.project.rickandmorty.app.ui.view.AppNavigationBar
import org.pet.project.rickandmorty.core.navigation.LocalNestedNavController
import org.pet.project.rickandmorty.core.navigation.rememberNestedNavController
import org.pet.project.rickandmorty.util.collectAsEffect

@Composable
internal fun MainScreen() {
    val nestedNavController = rememberNestedNavController()
    val viewModel = viewModel { MainViewModel() }
    val event = viewModel.event

    CompositionLocalProvider(
        LocalNestedNavController provides nestedNavController
    ) {
        MainScreen(
            event = event,
            onIntent = { viewModel.onIntent(it) }
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun MainScreen(
    event: Flow<MainEvent>,
    onIntent: (MainIntent) -> Unit
) {
    val navigator = rememberMainNavigator()

    Scaffold(
        bottomBar = {
            AppNavigationBar { tab ->
                onIntent(MainIntent.OpenAppTab(tab))
            }
        }
    ) { paddingValues ->
        val customPadding = PaddingValues(
            start = paddingValues.calculateLeftPadding(LayoutDirection.Ltr),
            top = 0.dp,
            bottom = paddingValues.calculateBottomPadding(),
            end = paddingValues.calculateRightPadding(LayoutDirection.Ltr)
        )

        NestedNavGraph(Modifier.padding(customPadding))
    }

    event.collectAsEffect { e ->
        when(e) {
            is MainEvent.OpenAppTab -> navigator.openTab(e.tab)
        }
    }
}