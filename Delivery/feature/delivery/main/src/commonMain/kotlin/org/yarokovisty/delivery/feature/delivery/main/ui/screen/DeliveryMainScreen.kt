package org.yarokovisty.delivery.feature.delivery.main.ui.screen

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.viewmodel.koinViewModel
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.screen.FullScreen
import org.yarokovisty.delivery.feature.delivery.main.presentation.intent.DeliveryMainIntent
import org.yarokovisty.delivery.feature.delivery.main.presentation.state.DeliveryMainState
import org.yarokovisty.delivery.feature.delivery.main.presentation.viewmodel.DeliveryMainViewModel
import org.yarokovisty.delivery.feature.delivery.main.ui.component.ContentScreen
import org.yarokovisty.delivery.feature.delivery.main.ui.component.FailureScreen

@Composable
internal fun DeliveryMainScreen() {
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

    FullScreen(containerColor = containerColor) {
        if (state.error) {
            FailureScreen(onIntent)
        } else {
            ContentScreen(state, onIntent)
        }

        state.deliveryCalculatorContent?.parcelInfoList?.let { parcelInfoList ->
            SelectParcelTypeScreen(
                visible = state.showSelectParcelType,
                parcelInfoList = parcelInfoList,
                onIntent = onIntent
            )
        }
    }
}
