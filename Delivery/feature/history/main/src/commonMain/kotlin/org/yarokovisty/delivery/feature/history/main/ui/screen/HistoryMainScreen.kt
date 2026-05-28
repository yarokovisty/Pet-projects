package org.yarokovisty.delivery.feature.history.main.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import delivery.feature.history.main.generated.resources.Res
import delivery.feature.history.main.generated.resources.history_main_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.yarokovisty.delivery.design.uikit.TopBar
import org.yarokovisty.delivery.design.uikit.screen.FullScreen
import org.yarokovisty.delivery.feature.history.main.presentation.intent.HistoryMainIntent
import org.yarokovisty.delivery.feature.history.main.presentation.state.HistoryMainState
import org.yarokovisty.delivery.feature.history.main.presentation.viewmodel.HistoryMainViewModel
import org.yarokovisty.delivery.feature.history.main.ui.component.EmptyContent
import org.yarokovisty.delivery.feature.history.main.ui.component.FailureContent
import org.yarokovisty.delivery.feature.history.main.ui.component.HistoryOrdersContent
import org.yarokovisty.delivery.feature.history.main.ui.component.LoadingContent

@Composable
internal fun HistoryMainScreen() {
    val viewModel: HistoryMainViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    HistoryMainScreen(state, viewModel::onIntent)
}

@Composable
private fun HistoryMainScreen(
    state: HistoryMainState,
    onIntent: (HistoryMainIntent) -> Unit
) {
    LaunchedEffect(Unit) {
        onIntent(HistoryMainIntent.LoadData)
    }

    FullScreen(
        paddingValues = WindowInsets.statusBars.asPaddingValues()
    ) {
        Column {
            TopBar(title = stringResource(Res.string.history_main_title))

            when {
                state.loading -> LoadingContent()
                state.error -> FailureContent(onRefreshClick = { onIntent(HistoryMainIntent.LoadData) })
                state.orders.isEmpty() -> EmptyContent()
                else -> HistoryOrdersContent(
                    state.orders,
                    onDetailClick = { order ->
                        onIntent(HistoryMainIntent.OpenOrderDetail(order.id))
                    }
                )
            }
        }
    }
}
