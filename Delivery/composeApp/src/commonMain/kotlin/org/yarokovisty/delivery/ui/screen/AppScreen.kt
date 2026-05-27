package org.yarokovisty.delivery.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import org.koin.compose.viewmodel.koinViewModel
import org.yarokovisty.delivery.feature.delivery.calculator.navigation.calculatorEntry
import org.yarokovisty.delivery.feature.delivery.direction.navigation.directionEntry
import org.yarokovisty.delivery.feature.delivery.order.navigation.confirmationOrderEntry
import org.yarokovisty.delivery.feature.delivery.order.navigation.successOrderEntry
import org.yarokovisty.delivery.feature.delivery.payer.navigation.payerEntry
import org.yarokovisty.delivery.feature.delivery.person.navigation.receiverEntry
import org.yarokovisty.delivery.feature.delivery.person.navigation.senderEntry
import org.yarokovisty.delivery.feature.delivery.point.navigation.receiverAddressEntry
import org.yarokovisty.delivery.feature.delivery.point.navigation.senderAddressEntry
import org.yarokovisty.delivery.feature.login.navigation.loginEntry
import org.yarokovisty.delivery.navigation.entry.mainEntry
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
            mainEntry()
            directionEntry()
            calculatorEntry()
            confirmationOrderEntry()
            payerEntry()
            receiverAddressEntry()
            receiverEntry()
            senderAddressEntry()
            senderEntry()
            successOrderEntry()
            loginEntry()
        },
    )
}
