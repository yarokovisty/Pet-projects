package org.yarokovisty.delivery.di.module

import org.koin.dsl.module
import org.yarokovisty.delivery.common.auth.di.authModule
import org.yarokovisty.delivery.common.delivery.calculator.di.deliveryCalculatorModule
import org.yarokovisty.delivery.common.delivery.direction.di.deliveryDirectionModule
import org.yarokovisty.delivery.common.delivery.order.di.deliveryOrderModule
import org.yarokovisty.delivery.common.delivery.parcel.di.deliveryParcelModule
import org.yarokovisty.delivery.common.delivery.payer.di.deliveryPayerModule
import org.yarokovisty.delivery.common.delivery.person.di.deliveryPersonModule
import org.yarokovisty.delivery.common.delivery.point.di.deliveryPointModule
import org.yarokovisty.delivery.common.delivery.step.deliveryStepModule
import org.yarokovisty.delivery.common.profile.main.di.profileMainModule
import org.yarokovisty.delivery.common.validation.di.validationModule

val commonModule = module {
    includes(
        authModule,
        deliveryCalculatorModule,
        deliveryDirectionModule,
        deliveryOrderModule,
        deliveryParcelModule,
        deliveryPayerModule,
        deliveryPersonModule,
        deliveryPointModule,
        deliveryStepModule,
        profileMainModule,
        validationModule
    )
}
