package org.yarokovisty.delivery.feature.delivery.payer.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import delivery.feature.delivery.payer.generated.resources.Res
import delivery.feature.delivery.payer.generated.resources.payer_step_indicator_title
import org.jetbrains.compose.resources.stringResource
import org.yarokovisty.delivery.common.delivery.presentation.StepState
import org.yarokovisty.delivery.design.uikit.LinearStepIndicator

@Composable
internal fun PayerLinearStepIndicator(state: StepState) {
    LinearStepIndicator(
        step = state.progress,
        maxSteps = state.maxProgress,
        title = stringResource(Res.string.payer_step_indicator_title, state.progress, state.maxProgress),
        modifier = Modifier.padding(vertical = 24.dp, horizontal = 16.dp)
    )
}
