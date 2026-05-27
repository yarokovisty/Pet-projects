package org.yarokovisty.delivery.feature.delivery.order.di

import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.yarokovisty.delivery.common.delivery.step.MaxStepQualifier
import org.yarokovisty.delivery.feature.delivery.order.presentation.viewmodel.ConfirmationOrderViewModel
import org.yarokovisty.delivery.feature.delivery.order.presentation.viewmodel.SuccessOrderViewModel
import org.yarokovisty.delivery.util.phone.PhoneNumberFormatter
import org.yarokovisty.delivery.util.phone.PhoneNumberMask

val deliveryOrderModule = module {
    viewModel {
        ConfirmationOrderViewModel(
            orderRepository = get(),
            getConfirmationOrderUseCase = get(),
            clearConfirmationOrderUseCase = get(),
            phoneNumberFormatter = PhoneNumberFormatter(PhoneNumberMask.RU),
            router = get(),
            maxSteps = get(named<MaxStepQualifier>())
        )
    }

    viewModelOf(::SuccessOrderViewModel)
}
