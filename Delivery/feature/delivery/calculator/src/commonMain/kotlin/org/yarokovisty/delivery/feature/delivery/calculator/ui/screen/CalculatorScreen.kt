package org.yarokovisty.delivery.feature.delivery.calculator.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import delivery.feature.delivery.calculator.generated.resources.Res
import delivery.feature.delivery.calculator.generated.resources.calculator_topbar_title
import delivery.feature.delivery.calculator.generated.resources.ic_close
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import org.yarokovisty.delivery.common.delivery.direction.domain.entity.DeliveryPoint
import org.yarokovisty.delivery.common.delivery.parcel.domain.entity.ParcelInfo
import org.yarokovisty.delivery.design.uikit.TopBar
import org.yarokovisty.delivery.design.uikit.screen.FullScreen
import org.yarokovisty.delivery.feature.delivery.calculator.presentation.intent.CalculatorIntent
import org.yarokovisty.delivery.feature.delivery.calculator.presentation.state.CalculatorState
import org.yarokovisty.delivery.feature.delivery.calculator.presentation.viewmodel.CalculatorViewModel
import org.yarokovisty.delivery.feature.delivery.calculator.ui.component.CalculatorLinearStepIndicator
import org.yarokovisty.delivery.feature.delivery.calculator.ui.component.FailureScreen
import org.yarokovisty.delivery.feature.delivery.calculator.ui.component.OptionListComponent
import org.yarokovisty.delivery.feature.delivery.calculator.ui.component.SkeletonScreen

@Composable
internal fun CalculatorScreen(
    parcelInfo: ParcelInfo,
    senderPoint: DeliveryPoint,
    receiverPoint: DeliveryPoint
) {
    val viewModel: CalculatorViewModel = koinViewModel { parametersOf(parcelInfo, senderPoint, receiverPoint) }
    val state by viewModel.state.collectAsState()

    CalculatorScreen(state, viewModel::onIntent)
}

@Composable
private fun CalculatorScreen(
    state: CalculatorState,
    onIntent: (CalculatorIntent) -> Unit
) {
    FullScreen {
        Column(modifier = Modifier.fillMaxSize()) {
            CalculatorTopBar(onBackClick = { onIntent(CalculatorIntent.Back) })

            when {
                state.error -> {
                    FailureScreen(onIntent)
                }

                state.skeleton -> {
                    CalculatorLinearStepIndicator(state.stepState)

                    SkeletonScreen()
                }

                else -> {
                    CalculatorLinearStepIndicator(state.stepState)

                    OptionListComponent(
                        options = state.options,
                        onSelectOption = { onIntent(CalculatorIntent.SelectOption(it)) }
                    )
                }
            }
        }
    }
}

@Composable
private fun CalculatorTopBar(onBackClick: () -> Unit) {
    TopBar(
        title = stringResource(Res.string.calculator_topbar_title),
        navigationIcon = painterResource(Res.drawable.ic_close),
        onClickNavIcon = onBackClick
    )
}
