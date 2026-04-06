package org.yarokovisty.delivery.di.module

import org.koin.core.module.Module
import org.koin.dsl.module
import org.yarokovisty.delivery.feature.delivery.main.api.navigation.DeliveryTab
import org.yarokovisty.delivery.libs.navigation.backstack.BottomBarBackStack
import org.yarokovisty.delivery.libs.navigation.backstack.GlobalBackStack
import org.yarokovisty.delivery.navigation.MainDestination

val navigationModule: Module = module {
    single { GlobalBackStack(MainDestination) }
    single { BottomBarBackStack(DeliveryTab) }
}
