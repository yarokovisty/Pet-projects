package org.yarokovisty.delivery.feature.delivery.point.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import delivery.design.resources.generated.resources.ic_arrow_left
import delivery.feature.delivery.point.generated.resources.Res
import delivery.feature.delivery.point.generated.resources.address_sender_topbar_title
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import org.yarokovisty.delivery.design.uikit.TopBar
import org.yarokovisty.delivery.design.uikit.screen.FullScreen
import org.yarokovisty.delivery.feature.delivery.point.navigation.AddressScreenType
import org.yarokovisty.delivery.feature.delivery.point.presentation.intent.SenderAddressIntent
import org.yarokovisty.delivery.feature.delivery.point.presentation.state.SenderAddressState
import org.yarokovisty.delivery.feature.delivery.point.presentation.viewmodel.SenderAddressViewModel
import org.yarokovisty.delivery.feature.delivery.point.ui.component.AddressLinearStepIndicator
import org.yarokovisty.delivery.feature.delivery.point.ui.component.SenderAddressContent
import delivery.design.resources.generated.resources.Res as DrawableRes

@Composable
internal fun SenderAddressScreen(screenType: AddressScreenType) {
    val viewModel: SenderAddressViewModel = koinViewModel { parametersOf(screenType) }
    val state by viewModel.state.collectAsState()

    SenderAddressScreen(state, viewModel::onIntent)
}

@Composable
private fun SenderAddressScreen(
    state: SenderAddressState,
    onIntent: (SenderAddressIntent) -> Unit
) {
    FullScreen {
        Column(modifier = Modifier.fillMaxSize()) {
            SenderAddressTopBar(onBackClick = { onIntent(SenderAddressIntent.Back) })

            AddressLinearStepIndicator(state.stepState)

            SenderAddressContent(state.contentState, onIntent)
        }
    }
}

@Composable
private fun SenderAddressTopBar(onBackClick: () -> Unit) {
    TopBar(
        title = stringResource(Res.string.address_sender_topbar_title),
        navigationIcon = painterResource(DrawableRes.drawable.ic_arrow_left),
        onClickNavIcon = onBackClick
    )
}
