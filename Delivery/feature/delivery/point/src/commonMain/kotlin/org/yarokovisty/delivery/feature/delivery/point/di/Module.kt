package org.yarokovisty.delivery.feature.delivery.point.di

import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.yarokovisty.delivery.common.delivery.step.MaxStepQualifier
import org.yarokovisty.delivery.feature.delivery.point.presentation.viewmodel.ReceiverAddressViewModel
import org.yarokovisty.delivery.feature.delivery.point.presentation.viewmodel.SenderAddressViewModel

val deliveryPointModule = module {
    viewModel {
        SenderAddressViewModel(
            addressRepository = get(),
            addressValidator = get(),
            router = get(),
            maxSteps = get(named<MaxStepQualifier>())
        )
    }

    viewModel {
        ReceiverAddressViewModel(
            addressRepository = get(),
            addressValidator = get(),
            router = get(),
            maxSteps = get(named<MaxStepQualifier>())
        )
    }
}
