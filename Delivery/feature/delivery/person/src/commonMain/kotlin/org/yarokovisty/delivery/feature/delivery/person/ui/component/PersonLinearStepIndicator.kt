package org.yarokovisty.delivery.feature.delivery.person.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import delivery.feature.delivery.person.generated.resources.Res
import delivery.feature.delivery.person.generated.resources.person_step_indicator_title
import org.jetbrains.compose.resources.stringResource
import org.yarokovisty.delivery.design.uikit.LinearStepIndicator
import org.yarokovisty.delivery.feature.delivery.person.presentation.state.StepState

@Composable
internal fun PersonLinearStepIndicator(state: StepState) {
    LinearStepIndicator(
        step = state.progress,
        maxSteps = state.maxProgress,
        title = stringResource(Res.string.person_step_indicator_title, state.progress, state.maxProgress),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp, horizontal = 16.dp)
    )
}
