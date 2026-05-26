package org.yarokovisty.delivery.feature.delivery.order.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigationevent.NavigationEventInfo
import androidx.navigationevent.compose.NavigationBackHandler
import androidx.navigationevent.compose.rememberNavigationEventState
import delivery.design.resources.generated.resources.ic_close
import delivery.feature.delivery.order.generated.resources.Res
import delivery.feature.delivery.order.generated.resources.order_checkout_error
import delivery.feature.delivery.order.generated.resources.order_topbar_title
import kotlinx.coroutines.flow.Flow
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.TopBar
import org.yarokovisty.delivery.design.uikit.screen.FullScreen
import org.yarokovisty.delivery.design.uikit.text.Paragraph14Regular
import org.yarokovisty.delivery.feature.delivery.order.presentation.event.ConfirmationOrderEvent
import org.yarokovisty.delivery.feature.delivery.order.presentation.intent.ConfirmationOrderIntent
import org.yarokovisty.delivery.feature.delivery.order.presentation.state.ConfirmationOrderState
import org.yarokovisty.delivery.feature.delivery.order.presentation.viewmodel.ConfirmationOrderViewModel
import org.yarokovisty.delivery.feature.delivery.order.ui.component.ConfirmationOrderContent
import org.yarokovisty.delivery.feature.delivery.order.ui.component.ConfirmationOrderLinearStepIndicator
import org.yarokovisty.delivery.util.flow.observe
import delivery.design.resources.generated.resources.Res as DrawableRes

@Composable
internal fun ConfirmationOrderScreen() {
    val viewModel: ConfirmationOrderViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    ConfirmationOrderScreen(state, viewModel.events, viewModel::onIntent)
}

@Composable
private fun ConfirmationOrderScreen(
    state: ConfirmationOrderState,
    events: Flow<ConfirmationOrderEvent>,
    onIntent: (ConfirmationOrderIntent) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        onIntent(ConfirmationOrderIntent.LoadData)
    }

    NavigationBackHandler(
        state = rememberNavigationEventState(NavigationEventInfo.None),
        onBackCompleted = { onIntent(ConfirmationOrderIntent.Back) }
    )

    FullScreen(paddingValues = WindowInsets.navigationBars.asPaddingValues()) {
        Column(modifier = Modifier.fillMaxSize()) {
            ConfirmationOrderTopBar(onBackClick = { onIntent(ConfirmationOrderIntent.Back) })

            ConfirmationOrderLinearStepIndicator(state.stepState)

            state.content?.let {
                ConfirmationOrderContent(it, onIntent)
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        ) { snackbarData ->
            ErrorSnackbar(text = snackbarData.visuals.message)
        }
    }

    events observe { event ->
        when (event) {
            ConfirmationOrderEvent.CheckoutOrderError -> {
                val message = getString(Res.string.order_checkout_error)
                snackbarHostState.showSnackbar(message)
            }
        }
    }
}

@Composable
private fun ConfirmationOrderTopBar(onBackClick: () -> Unit) {
    TopBar(
        title = stringResource(Res.string.order_topbar_title),
        navigationIcon = painterResource(DrawableRes.drawable.ic_close),
        onNavIconClick = onBackClick
    )
}

@Composable
private fun ErrorSnackbar(text: String) {
    Snackbar(
        containerColor = DeliveryTheme.colorScheme.bgSecondary,
        modifier = Modifier.padding(8.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Paragraph14Regular(text)
        }
    }
}
