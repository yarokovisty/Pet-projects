package org.yarokovisty.delivery.di.module

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.yarokovisty.delivery.feature.delivery.direction.navigation.DirectionRouter
import org.yarokovisty.delivery.feature.delivery.main.navigation.DeliveryRouter
import org.yarokovisty.delivery.feature.delivery.main.navigation.DeliveryTab
import org.yarokovisty.delivery.feature.login.navigation.LoginRouter
import org.yarokovisty.delivery.feature.profile.main.navigation.ProfileRouter
import org.yarokovisty.delivery.libs.navigation.backstack.BottomBarBackStack
import org.yarokovisty.delivery.libs.navigation.backstack.GlobalBackStack
import org.yarokovisty.delivery.navigation.destination.MainDestination
import org.yarokovisty.delivery.navigation.router.DeliveryRouterImpl
import org.yarokovisty.delivery.navigation.router.DirectionRouterImpl
import org.yarokovisty.delivery.navigation.router.LoginRouterImpl
import org.yarokovisty.delivery.navigation.router.ProfileRouterImpl

private val backStackModule = module {
    single { GlobalBackStack(MainDestination) }
    single { BottomBarBackStack(DeliveryTab) }
}

private val routerModule = module {
    factoryOf(::DeliveryRouterImpl) bind DeliveryRouter::class
    factoryOf(::DirectionRouterImpl) bind DirectionRouter::class
    factoryOf(::ProfileRouterImpl) bind ProfileRouter::class
    factoryOf(::LoginRouterImpl) bind LoginRouter::class
}

val navigationModule = module {
    includes(backStackModule, routerModule)
}
