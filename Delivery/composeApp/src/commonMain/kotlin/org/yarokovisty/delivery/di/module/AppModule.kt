package org.yarokovisty.delivery.di.module

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.yarokovisty.delivery.feature.delivery.main.api.navigation.DeliveryTab
import org.yarokovisty.delivery.libs.navigation.backstack.BottomBarBackStack
import org.yarokovisty.delivery.libs.navigation.backstack.GlobalBackStack
import org.yarokovisty.delivery.navigation.MainDestination
import org.yarokovisty.delivery.presentation.router.AppRouter
import org.yarokovisty.delivery.presentation.router.MainRouter
import org.yarokovisty.delivery.presentation.viewmodel.AppViewModel
import org.yarokovisty.delivery.presentation.viewmodel.MainViewModel

val appModule = module {
    single { GlobalBackStack(MainDestination) }
    single { BottomBarBackStack(DeliveryTab) }

    factoryOf(::AppRouter)
    factoryOf(::MainRouter)

    viewModelOf(::AppViewModel)
    viewModelOf(::MainViewModel)
}
