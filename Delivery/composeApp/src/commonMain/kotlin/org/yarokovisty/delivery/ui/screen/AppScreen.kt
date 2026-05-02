package org.yarokovisty.delivery.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import org.koin.compose.viewmodel.koinViewModel
import org.yarokovisty.delivery.feature.delivery.calculator.navigation.CalculatorDestination
import org.yarokovisty.delivery.feature.delivery.calculator.ui.screen.CalculatorScreen
import org.yarokovisty.delivery.feature.delivery.direction.navigation.DirectionDestination
import org.yarokovisty.delivery.feature.delivery.direction.ui.screen.DirectionScreen
import org.yarokovisty.delivery.feature.delivery.person.navigation.ReceiverDestination
import org.yarokovisty.delivery.feature.delivery.person.ui.screen.ReceiverScreen
import org.yarokovisty.delivery.feature.login.navigation.LoginDestination
import org.yarokovisty.delivery.feature.login.ui.screen.LoginScreen
import org.yarokovisty.delivery.navigation.destination.MainDestination
import org.yarokovisty.delivery.presentation.intent.AppIntent
import org.yarokovisty.delivery.presentation.state.AppState
import org.yarokovisty.delivery.presentation.viewmodel.AppViewModel
import kotlin.collections.listOf

@Composable
fun AppScreen() {
    val viewModel: AppViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    AppScreen(
        state = state,
        onIntent = viewModel::onIntent
    )
}

@Composable
private fun AppScreen(
    state: AppState,
    onIntent: (AppIntent) -> Unit
) {
    NavDisplay(
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        backStack = state.backStack,
        onBack = {
            onIntent(AppIntent.Back)
        },
        entryProvider = entryProvider {
            entry<MainDestination> {
                MainScreen()
            }
            entry<DirectionDestination> { destination ->
                DirectionScreen(destination.directionType)
            }
            entry<CalculatorDestination> { destination ->
                CalculatorScreen(
                    parcelInfo = destination.parcelInfo,
                    receiverPoint = destination.receiverPoint,
                    senderPoint = destination.senderPoint
                )
            }
            entry<ReceiverDestination> {
                ReceiverScreen()
            }
            entry<LoginDestination> {
                LoginScreen()
            }
        },
    )
}
