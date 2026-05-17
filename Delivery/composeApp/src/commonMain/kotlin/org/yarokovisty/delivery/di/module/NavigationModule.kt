package org.yarokovisty.delivery.di.module

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.yarokovisty.delivery.feature.delivery.calculator.navigation.CalculatorRouter
import org.yarokovisty.delivery.feature.delivery.direction.navigation.DirectionRouter
import org.yarokovisty.delivery.feature.delivery.main.navigation.DeliveryRouter
import org.yarokovisty.delivery.feature.delivery.main.navigation.DeliveryTab
import org.yarokovisty.delivery.feature.delivery.order.navigation.ConfirmationOrderRouter
import org.yarokovisty.delivery.feature.delivery.payer.navigation.PayerRouter
import org.yarokovisty.delivery.feature.delivery.person.navigation.ReceiverRouter
import org.yarokovisty.delivery.feature.delivery.person.navigation.SenderRouter
import org.yarokovisty.delivery.feature.delivery.point.navigation.ReceiverAddressRouter
import org.yarokovisty.delivery.feature.delivery.point.navigation.SenderAddressRouter
import org.yarokovisty.delivery.feature.login.navigation.LoginRouter
import org.yarokovisty.delivery.feature.profile.main.navigation.ProfileRouter
import org.yarokovisty.delivery.libs.navigation.backstack.BottomBarBackStack
import org.yarokovisty.delivery.libs.navigation.backstack.GlobalBackStack
import org.yarokovisty.delivery.navigation.destination.MainDestination
import org.yarokovisty.delivery.navigation.router.CalculatorRouterImpl
import org.yarokovisty.delivery.navigation.router.ConfirmationOrderRouterImpl
import org.yarokovisty.delivery.navigation.router.DeliveryRouterImpl
import org.yarokovisty.delivery.navigation.router.DirectionRouterImpl
import org.yarokovisty.delivery.navigation.router.LoginRouterImpl
import org.yarokovisty.delivery.navigation.router.PayerRouterImpl
import org.yarokovisty.delivery.navigation.router.ProfileRouterImpl
import org.yarokovisty.delivery.navigation.router.ReceiverAddressRouterImpl
import org.yarokovisty.delivery.navigation.router.ReceiverRouterImpl
import org.yarokovisty.delivery.navigation.router.SenderAddressRouterImpl
import org.yarokovisty.delivery.navigation.router.SenderRouterImpl

private val backStackModule = module {
    single { GlobalBackStack(MainDestination) }
    single { BottomBarBackStack(DeliveryTab) }
}

private val routerModule = module {
    factoryOf(::CalculatorRouterImpl) bind CalculatorRouter::class
    factoryOf(::ConfirmationOrderRouterImpl) bind ConfirmationOrderRouter::class
    factoryOf(::DeliveryRouterImpl) bind DeliveryRouter::class
    factoryOf(::DirectionRouterImpl) bind DirectionRouter::class
    factoryOf(::LoginRouterImpl) bind LoginRouter::class
    factoryOf(::PayerRouterImpl) bind PayerRouter::class
    factoryOf(::ProfileRouterImpl) bind ProfileRouter::class
    factoryOf(::ReceiverAddressRouterImpl) bind ReceiverAddressRouter::class
    factoryOf(::ReceiverRouterImpl) bind ReceiverRouter::class
    factoryOf(::SenderAddressRouterImpl) bind SenderAddressRouter::class
    factoryOf(::SenderRouterImpl) bind SenderRouter::class
}

val navigationModule = module {
    includes(backStackModule, routerModule)
}
