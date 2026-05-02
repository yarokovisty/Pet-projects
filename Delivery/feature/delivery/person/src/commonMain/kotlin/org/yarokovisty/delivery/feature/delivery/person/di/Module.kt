package org.yarokovisty.delivery.feature.delivery.person.di

import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.yarokovisty.delivery.common.delivery.step.MaxStepQualifier
import org.yarokovisty.delivery.feature.delivery.person.presentation.viewmodel.ReceiverViewModel
import org.yarokovisty.delivery.feature.delivery.person.presentation.viewmodel.SenderViewModel
import org.yarokovisty.delivery.util.phone.PhoneNumberFormatter
import org.yarokovisty.delivery.util.phone.PhoneNumberMask

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
    viewModel {
        SenderViewModel(
            personRepository = get(),
            getUserUseCase = get(),
            ruPhoneValidateUseCase = get(),
            nameValidator = get(),
            phoneNumberFormatter = PhoneNumberFormatter(PhoneNumberMask.RU),
            router = get(),
            maxSteps = get(named<MaxStepQualifier>())
        )
    }
}
