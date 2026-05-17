package org.yarokovisty.delivery.feature.delivery.order.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import delivery.feature.delivery.order.generated.resources.Res
import delivery.feature.delivery.order.generated.resources.order_step_indicator_title
import org.jetbrains.compose.resources.stringResource
import org.yarokovisty.delivery.common.delivery.presentation.StepState
import org.yarokovisty.delivery.design.uikit.LinearStepIndicator

@Composable
internal fun ConfirmationOrderLinearStepIndicator(state: StepState) {
    LinearStepIndicator(
        step = state.progress,
        maxSteps = state.maxProgress,
        title = stringResource(Res.string.order_step_indicator_title, state.progress, state.maxProgress),
        modifier = Modifier.padding(vertical = 24.dp, horizontal = 16.dp)
    )
}
