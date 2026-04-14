package org.yarokovisty.delivery.di.module

import org.koin.dsl.module
import org.yarokovisty.common.delivery.calculator.di.deliveryCalculatorModule
import org.yarokovisty.common.delivery.direction.di.deliveryDirectionModule
import org.yarokovisty.common.delivery.parcel.di.deliveryParcelModule
import org.yarokovisty.delivery.common.auth.di.authModule
import org.yarokovisty.delivery.common.validation.di.validationModule

val commonModule = module {
    includes(
        authModule,
        deliveryCalculatorModule,
        deliveryDirectionModule,
        deliveryParcelModule,
        validationModule
    )
}
