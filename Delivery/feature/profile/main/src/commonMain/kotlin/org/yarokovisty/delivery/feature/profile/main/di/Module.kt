package org.yarokovisty.delivery.feature.profile.main.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.yarokovisty.delivery.feature.profile.main.presentation.viewmodel.ProfileViewModel

val profileMainModule = module {
    viewModelOf(::ProfileViewModel)
}
