package org.yarokovisty.delivery.di.module

import org.koin.dsl.module
import org.yarokovisty.delivery.feature.delivery.main.impl.di.deliveryMainModule
import org.yarokovisty.delivery.feature.direction.impl.di.deliveryDirectionModule
import org.yarokovisty.delivery.feature.login.impl.di.loginModule
import org.yarokovisty.delivery.feature.profile.main.impl.di.profileMainModule

val featureModule = module {
    includes(
        deliveryDirectionModule,
        deliveryMainModule,
        loginModule,
        profileMainModule,
    )
}
