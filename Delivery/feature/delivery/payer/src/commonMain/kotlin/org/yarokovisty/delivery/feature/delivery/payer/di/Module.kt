package org.yarokovisty.delivery.feature.delivery.payer.di

import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.yarokovisty.delivery.common.delivery.step.MaxStepQualifier
import org.yarokovisty.delivery.feature.delivery.payer.presentation.viewmodel.PayerViewModel

val deliveryPayerModule = module {
    viewModel {
        PayerViewModel(
            payerRepository = get(),
            router = get(),
            maxSteps = get(named<MaxStepQualifier>())
        )
    }
}
