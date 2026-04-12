package org.yarokovisty.delivery.feature.delivery.main.impl.ui.screen

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.ExperimentalMaterial3Api
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DeliveryMainScreen(
    state: DeliveryMainState,
    onIntent: (DeliveryMainIntent) -> Unit
) {
    val containerColor = if (isSystemInDarkTheme()) {
        DeliveryTheme.colorScheme.bgPrimary
    } else {
        DeliveryTheme.colorScheme.bgSecondary
    }

    FullScreen(
        containerColor = containerColor,
        paddingValues = WindowInsets.statusBars.asPaddingValues(),
    ) {
        if (state.error) {
            FailureScreen(onIntent)
        } else {
            ContentScreen(state, onIntent)
        }

        if (state.showSelectParcelType && state.deliveryCalculatorContent?.parcelInfos != null) {
            SelectParcelTypeScreen(
                parcelInfos = state.deliveryCalculatorContent.parcelInfos,
                onIntent = onIntent
            )
        }
    }
}
