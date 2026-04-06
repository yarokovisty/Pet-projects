package org.yarokovisty.delivery.di.module

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.yarokovisty.delivery.presentation.router.AppRouter
import org.yarokovisty.delivery.presentation.router.MainRouter
import org.yarokovisty.delivery.presentation.viewmodel.AppViewModel
import org.yarokovisty.delivery.presentation.viewmodel.MainViewModel

val appModule = module {
    factoryOf(::AppRouter)
    factoryOf(::MainRouter)

    viewModelOf(::AppViewModel)
    viewModelOf(::MainViewModel)
}
