package org.yarokovisty.delivery.di.module

import org.koin.dsl.module
import org.yarokovisty.delivery.feature.delivery.calculator.di.deliveryCalculatorModule
import org.yarokovisty.delivery.feature.delivery.direction.di.deliveryDirectionModule
import org.yarokovisty.delivery.feature.delivery.main.di.deliveryMainModule
import org.yarokovisty.delivery.feature.delivery.person.di.deliveryPersonModule
import org.yarokovisty.delivery.feature.delivery.point.di.deliveryPointModule
import org.yarokovisty.delivery.feature.login.di.loginModule
import org.yarokovisty.delivery.feature.profile.main.di.profileMainModule

val featureModule = module {
    includes(
        deliveryCalculatorModule,
        deliveryDirectionModule,
        deliveryMainModule,
        deliveryPersonModule,
        deliveryPointModule,
        loginModule,
        profileMainModule,
    )
}
