package org.yarokovisty.delivery.common.logout.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import org.yarokovisty.delivery.common.logout.domain.usecase.LogoutUseCase

val logoutModule = module {
    factoryOf(::LogoutUseCase)
}
