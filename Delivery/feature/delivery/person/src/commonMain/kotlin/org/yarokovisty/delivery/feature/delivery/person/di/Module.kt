package org.yarokovisty.delivery.feature.delivery.person.di

import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.yarokovisty.delivery.common.delivery.step.MaxStepQualifier
import org.yarokovisty.delivery.feature.delivery.person.presentation.viewmodel.ReceiverViewModel

val deliveryPersonModule = module {

    viewModel {
        ReceiverViewModel(
            personRepository = get(),
            ruPhoneValidateUseCase = get(),
            nameValidator = get(),
            router = get(),
            maxSteps = get(named<MaxStepQualifier>())
        )
    }
}
