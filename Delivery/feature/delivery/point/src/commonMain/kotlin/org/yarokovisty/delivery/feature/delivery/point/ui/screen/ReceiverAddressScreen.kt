package org.yarokovisty.delivery.feature.delivery.point.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import delivery.design.resources.generated.resources.ic_arrow_left
import delivery.feature.delivery.point.generated.resources.Res
import delivery.feature.delivery.point.generated.resources.address_receiver_topbar_title
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import org.yarokovisty.delivery.design.uikit.TopBar
import org.yarokovisty.delivery.design.uikit.screen.FullScreen
import org.yarokovisty.delivery.feature.delivery.point.navigation.AddressScreenType
import org.yarokovisty.delivery.feature.delivery.point.presentation.intent.ReceiverAddressIntent
import org.yarokovisty.delivery.feature.delivery.point.presentation.state.ReceiverAddressState
import org.yarokovisty.delivery.feature.delivery.point.presentation.viewmodel.ReceiverAddressViewModel
import org.yarokovisty.delivery.feature.delivery.point.ui.component.AddressLinearStepIndicator
import org.yarokovisty.delivery.feature.delivery.point.ui.component.ReceiverAddressContent
import delivery.design.resources.generated.resources.Res as DrawableRes

@Composable
internal fun ReceiverAddressScreen(screenType: AddressScreenType) {
    val viewModel: ReceiverAddressViewModel = koinViewModel { parametersOf(screenType) }
    val state by viewModel.state.collectAsState()

    ReceiverAddressScreen(state, viewModel::onIntent)
}

@Composable
private fun ReceiverAddressScreen(
    state: ReceiverAddressState,
    onIntent: (ReceiverAddressIntent) -> Unit
) {
    FullScreen {
        Column(modifier = Modifier.fillMaxSize()) {
            ReceiverAddressTopBar(onBackClick = { onIntent(ReceiverAddressIntent.Back) })

            AddressLinearStepIndicator(state.stepState)

            ReceiverAddressContent(state.contentState, onIntent)
        }
    }
}

@Composable
private fun ReceiverAddressTopBar(onBackClick: () -> Unit) {
    TopBar(
        title = stringResource(Res.string.address_receiver_topbar_title),
        navigationIcon = painterResource(DrawableRes.drawable.ic_arrow_left),
        onNavIconClick = onBackClick
    )
}
