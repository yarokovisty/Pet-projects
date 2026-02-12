package org.yarokovisty.delivery.feature.delivery.main.impl.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.viewmodel.koinViewModel
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.FullScreen
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.intent.DeliveryMainIntent
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.state.DeliveryMainState
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.viewmodel.DeliveryMainViewModel
import org.yarokovisty.delivery.feature.delivery.main.impl.ui.component.ContentScreen
import org.yarokovisty.delivery.feature.delivery.main.impl.ui.component.FailureScreen

@Composable
fun DeliveryMainScreen() {
    val viewModel: DeliveryMainViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    DeliveryMainScreen(state, viewModel::onIntent)
}

@Composable
private fun DeliveryMainScreen(
    state: DeliveryMainState,
    onIntent: (DeliveryMainIntent) -> Unit
) {
    FullScreen(DeliveryTheme.colorScheme.bgSecondary) {
        if (state.error) {
            FailureScreen(onIntent)
        } else {
            ContentScreen(state, onIntent)
        }
    }
}
