package org.yarokovisty.delivery.feature.delivery.payer.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import delivery.design.resources.generated.resources.ic_arrow_left
import delivery.feature.delivery.payer.generated.resources.Res
import delivery.feature.delivery.payer.generated.resources.payer_topbar_title
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.yarokovisty.delivery.design.uikit.TopBar
import org.yarokovisty.delivery.design.uikit.screen.FullScreen
import org.yarokovisty.delivery.feature.delivery.payer.presentation.intent.PayerIntent
import org.yarokovisty.delivery.feature.delivery.payer.presentation.state.PayerState
import org.yarokovisty.delivery.feature.delivery.payer.presentation.viewmodel.PayerViewModel
import org.yarokovisty.delivery.feature.delivery.payer.ui.component.PayerContent
import org.yarokovisty.delivery.feature.delivery.payer.ui.component.PayerLinearStepIndicator
import delivery.design.resources.generated.resources.Res as DrawableRes

@Composable
internal fun PayerScreen() {
    val viewModel: PayerViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    PayerScreen(state, viewModel::onIntent)
}

@Composable
private fun PayerScreen(
    state: PayerState,
    onIntent: (PayerIntent) -> Unit
) {
    FullScreen {
        Column(modifier = Modifier.fillMaxSize()) {
            PayerTopBar(onBackClick = { onIntent(PayerIntent.Back) })

            PayerLinearStepIndicator(state.stepState)

            PayerContent(state.content, onIntent)
        }
    }
}

@Composable
private fun PayerTopBar(onBackClick: () -> Unit) {
    TopBar(
        title = stringResource(Res.string.payer_topbar_title),
        navigationIcon = painterResource(DrawableRes.drawable.ic_arrow_left),
        onClickNavIcon = onBackClick
    )
}
