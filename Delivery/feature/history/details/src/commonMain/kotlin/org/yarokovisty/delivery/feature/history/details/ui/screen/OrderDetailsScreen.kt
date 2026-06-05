package org.yarokovisty.delivery.feature.history.details.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import org.yarokovisty.delivery.design.uikit.screen.FullLoadingScreen
import org.yarokovisty.delivery.design.uikit.screen.FullScreen
import org.yarokovisty.delivery.feature.history.details.presentation.intent.OrderDetailsIntent
import org.yarokovisty.delivery.feature.history.details.presentation.state.Error
import org.yarokovisty.delivery.feature.history.details.presentation.state.OrderDetailsState
import org.yarokovisty.delivery.feature.history.details.presentation.viewmodel.OrderDetailsViewModel
import org.yarokovisty.delivery.feature.history.details.ui.component.CancelErrorScreen
import org.yarokovisty.delivery.feature.history.details.ui.component.LoadErrorScreen
import org.yarokovisty.delivery.feature.history.details.ui.component.OrderDetailsContent
import org.yarokovisty.delivery.feature.history.details.ui.component.SuccessfulScreen
import org.yarokovisty.delivery.feature.history.details.ui.component.UnauthorizedErrorScreen

@Composable
internal fun OrderDetailsScreen(orderId: String) {
    val viewModel: OrderDetailsViewModel = koinViewModel { parametersOf(orderId) }
    val state by viewModel.state.collectAsStateWithLifecycle()

    OrderDetailsScreen(state, viewModel::onIntent)
}

@Composable
private fun OrderDetailsScreen(
    state: OrderDetailsState,
    onIntent: (OrderDetailsIntent) -> Unit
) {
    FullScreen {
        when {
            state.loading -> FullLoadingScreen()
            state.error != null -> OrderDetailsErrorScreen(state.error, onIntent)
            state.successfulScreenVisible -> SuccessfulScreen(onIntent = onIntent)
            state.order != null -> OrderDetailsContent(
                order = state.order,
                onBack = { onIntent(OrderDetailsIntent.Back) },
                onCancel = { onIntent(OrderDetailsIntent.OpenCancellationScreen) }
            )
        }

        CancellationScreen(state.cancellationScreenVisible, onIntent)
    }
}

@Composable
private fun OrderDetailsErrorScreen(
    error: Error,
    onIntent: (OrderDetailsIntent) -> Unit
) {
    when (error) {
        Error.Cancel -> CancelErrorScreen(onIntent = onIntent)
        Error.Load -> LoadErrorScreen(
            onCloseClick = { onIntent(OrderDetailsIntent.Back) },
            onRefreshClick = { onIntent(OrderDetailsIntent.LoadData) }
        )
        Error.Unauthorized -> UnauthorizedErrorScreen(
            onBack = { onIntent(OrderDetailsIntent.Back) },
            onLoginClick = { onIntent(OrderDetailsIntent.OpenLoginScreen) }
        )
    }
}
