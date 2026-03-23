package org.yarokovisty.delivery.ui.screen

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import org.koin.compose.viewmodel.koinViewModel
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.feature.delivery.main.api.navigation.DeliveryMainDestination
import org.yarokovisty.delivery.feature.delivery.main.impl.ui.screen.DeliveryMainScreen
import org.yarokovisty.delivery.navigation.HistoryMainDestination
import org.yarokovisty.delivery.navigation.ProfileMainDestination
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
    Scaffold(
        bottomBar = {
            BottomBar(
                selectedTab = state.selectedTab,
                onTabSelected = {
                    onIntent(MainIntent.SwitchTab(it))
                }
            )
        },
        containerColor = DeliveryTheme.colorScheme.bgPrimary
    ) { innerPadding ->
        NavDisplay(
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            backStack = state.backStack,
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(WindowInsets.statusBars),
            onBack = {
                onIntent(MainIntent.Back)
            },
            entryProvider = entryProvider {
                entry<DeliveryMainDestination> {
                    DeliveryMainScreen()
                }
                entry<HistoryMainDestination> {
                    Text("History")
                }
                entry<ProfileMainDestination> {
                    Text("Profile")
                }
            }
        )
    }
}
