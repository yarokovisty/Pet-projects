package org.pet.project.rickandmorty.app.ui.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.Flow
import org.pet.project.rickandmorty.app.navigation.NestedNavGraph
import org.pet.project.rickandmorty.app.navigation.main.rememberMainNavigator
import org.pet.project.rickandmorty.app.presentation.event.MainEvent
import org.pet.project.rickandmorty.app.presentation.intent.MainIntent
import org.pet.project.rickandmorty.app.presentation.viewmodel.MainViewModel
import org.pet.project.rickandmorty.app.ui.view.AppNavigationBar
import org.pet.project.rickandmorty.core.navigation.navigator.LocalNestedNavigator
import org.pet.project.rickandmorty.core.navigation.navigator.rememberNestedNavigator
import org.pet.project.rickandmorty.util.observe

@Composable
internal fun MainScreen() {
    val nestedNavController = rememberNestedNavigator()
    val viewModel = viewModel { MainViewModel() }
    val event = viewModel.event

    CompositionLocalProvider(LocalNestedNavigator provides nestedNavController) {
        MainScreen(
            event = event,
            onIntent = viewModel::onIntent
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
            start = paddingValues.calculateLeftPadding(LocalLayoutDirection.current),
            top = 0.dp,
            bottom = paddingValues.calculateBottomPadding(),
            end = paddingValues.calculateRightPadding(LocalLayoutDirection.current)
        )

        NestedNavGraph(Modifier.padding(customPadding))
    }

    event observe { e ->
        when(e) {
            is MainEvent.OpenAppTab -> navigator.openTab(e.tab)
        }
    }
}