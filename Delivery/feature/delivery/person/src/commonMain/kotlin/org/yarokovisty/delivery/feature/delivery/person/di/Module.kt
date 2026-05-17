package org.yarokovisty.delivery.feature.delivery.person.di

import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.yarokovisty.delivery.common.delivery.step.MaxStepQualifier
import org.yarokovisty.delivery.feature.delivery.person.navigation.PersonScreenType
import org.yarokovisty.delivery.feature.delivery.person.presentation.viewmodel.ReceiverViewModel
import org.yarokovisty.delivery.feature.delivery.person.presentation.viewmodel.SenderViewModel
import org.yarokovisty.delivery.util.phone.PhoneNumberFormatter
import org.yarokovisty.delivery.util.phone.PhoneNumberMask

val deliveryPersonModule = module {

    viewModel { (screenType: PersonScreenType) ->
        ReceiverViewModel(
            personRepository = get(),
            ruPhoneValidateUseCase = get(),
            nameValidator = get(),
            phoneNumberFormatter = PhoneNumberFormatter(PhoneNumberMask.RU),
            router = get(),
            screenType = screenType,
            maxSteps = get(named<MaxStepQualifier>())
        )
    }
    viewModel { (screenType: PersonScreenType) ->
        SenderViewModel(
            personRepository = get(),
            getUserUseCase = get(),
            ruPhoneValidateUseCase = get(),
            nameValidator = get(),
            phoneNumberFormatter = PhoneNumberFormatter(PhoneNumberMask.RU),
            router = get(),
            screenType = screenType,
            maxSteps = get(named<MaxStepQualifier>())
        )
    }
}
