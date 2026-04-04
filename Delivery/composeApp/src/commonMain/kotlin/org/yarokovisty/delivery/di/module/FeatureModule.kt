package org.yarokovisty.delivery.di.module

import org.koin.dsl.module
import org.yarokovisty.delivery.feature.delivery.main.impl.di.deliveryMainModule
import org.yarokovisty.delivery.feature.direction.impl.di.directionModule
import org.yarokovisty.delivery.feature.login.impl.di.loginModule

val featureModule = module {
    includes(
        directionModule,
        deliveryMainModule,
        loginModule,
    )
}
