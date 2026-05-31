package org.yarokovisty.delivery.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import org.koin.compose.viewmodel.koinViewModel
import org.yarokovisty.delivery.design.uikit.screen.FullScreen
import org.yarokovisty.delivery.feature.delivery.main.navigation.deliveryMainEntry
import org.yarokovisty.delivery.feature.history.main.navigation.historyMainEntry
import org.yarokovisty.delivery.feature.profile.main.navigation.profileEntry
import org.yarokovisty.delivery.presentation.intent.MainIntent
import org.yarokovisty.delivery.presentation.state.MainState
import org.yarokovisty.delivery.presentation.viewmodel.MainViewModel
import org.yarokovisty.delivery.ui.component.BottomBar

@Composable
fun MainScreen() {
    val viewModel: MainViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    MainScreen(
        state = state,
        onIntent = viewModel::onIntent,
    )
}

@Composable
private fun MainScreen(
    state: MainState,
    onIntent: (MainIntent) -> Unit,
) {
    FullScreen {
        Column {
            NavDisplay(
                entryDecorators = listOf(
                    rememberSaveableStateHolderNavEntryDecorator(),
                    rememberViewModelStoreNavEntryDecorator()
                ),
                backStack = state.backStack,
                modifier = Modifier.weight(1f),
                onBack = {
                    onIntent(MainIntent.Back)
                },
                entryProvider = entryProvider {
                    deliveryMainEntry()
                    historyMainEntry()
                    profileEntry()
                }
            )
            BottomBar(
                selectedTab = state.selectedTab,
                onTabSelected = { onIntent(MainIntent.SwitchTab(it)) }
            )
        }
    }
}
