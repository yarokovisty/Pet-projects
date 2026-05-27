package org.yarokovisty.delivery.feature.profile.main.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.yarokovisty.delivery.feature.profile.main.presentation.viewmodel.ProfileViewModel
import org.yarokovisty.delivery.util.phone.PhoneNumberFormatter
import org.yarokovisty.delivery.util.phone.PhoneNumberMask

val profileMainModule = module {
    viewModel {
        ProfileViewModel(
            getUserUseCase = get(),
            updateUserUseCase = get(),
            logoutUseCase = get(),
            emailValidator = get(),
            phoneNumberFormatter = PhoneNumberFormatter(PhoneNumberMask.RU),
            router = get(),
        )
    }
}
