package org.yarokovisty.delivery.feature.delivery.order.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import delivery.design.resources.generated.resources.Res
import delivery.design.resources.generated.resources.ic_close
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.yarokovisty.delivery.design.uikit.TopBar
import org.yarokovisty.delivery.design.uikit.screen.FullScreen
import org.yarokovisty.delivery.feature.delivery.order.presentation.intent.SuccessOrderIntent
import org.yarokovisty.delivery.feature.delivery.order.presentation.viewmodel.SuccessOrderViewModel
import org.yarokovisty.delivery.feature.delivery.order.ui.component.SuccessOrderContent

@Composable
internal fun SuccessOrderScreen() {
    val viewModel: SuccessOrderViewModel = koinViewModel()

    SuccessOrderScreen(viewModel::onIntent)
}

@Composable
private fun SuccessOrderScreen(onIntent: (SuccessOrderIntent) -> Unit) {
    FullScreen {
        Column {
            SuccessOrderTopBar(onBackClick = { onIntent(SuccessOrderIntent.Back) })

            SuccessOrderContent(
                onIntent = onIntent,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun SuccessOrderTopBar(onBackClick: () -> Unit) {
    TopBar(
        navigationIcon = painterResource(Res.drawable.ic_close),
        onNavIconClick = onBackClick,
    )
}
