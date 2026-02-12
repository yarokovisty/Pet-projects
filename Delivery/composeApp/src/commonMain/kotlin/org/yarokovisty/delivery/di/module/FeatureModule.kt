package org.yarokovisty.delivery.di.module

import org.koin.dsl.module
import org.yarokovisty.delivery.feature.delivery.main.impl.di.deliveryMainModule

val featureModule = module {
    includes(
        deliveryMainModule,
    )
}
