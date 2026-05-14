package org.yarokovisty.delivery.feature.delivery.payer.presentation.state

import org.yarokovisty.delivery.common.delivery.payer.domain.entity.Payer
import org.yarokovisty.delivery.common.delivery.presentation.StepState

internal fun initial(currentStep: Int, maxSteps: Int): PayerState =
    PayerState(
        stepState = StepState(
            progress = currentStep,
            maxProgress = maxSteps
        ),
        content = ContentState(
            selectedPayer = Payer.RECEIVER,
            payers = Payer.entries
        )
    )

internal fun PayerState.changePayer(payer: Payer): PayerState =
    copy(
        content = content.copy(selectedPayer = payer)
    )
